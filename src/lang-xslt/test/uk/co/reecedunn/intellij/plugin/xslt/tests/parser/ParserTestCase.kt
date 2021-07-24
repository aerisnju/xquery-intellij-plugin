/*
 * Copyright (C) 2019-2020 Reece H. Dunn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.reecedunn.intellij.plugin.xslt.tests.parser

import com.intellij.compat.testFramework.registerServiceInstance
import com.intellij.lang.LanguageASTFactory
import com.intellij.lang.ParserDefinition
import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.lang.xml.XMLLanguage
import com.intellij.lang.xml.XmlASTFactory
import com.intellij.openapi.Disposable
import com.intellij.openapi.extensions.DefaultPluginDescriptor
import com.intellij.openapi.extensions.PluginDescriptor
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.xml.StartTagEndTokenProvider
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.XmlExtension
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import uk.co.reecedunn.intellij.plugin.core.extensions.PluginDescriptorProvider
import uk.co.reecedunn.intellij.plugin.core.sequences.walkTree
import uk.co.reecedunn.intellij.plugin.core.tests.injecton.MockInjectedLanguageManager
import uk.co.reecedunn.intellij.plugin.core.tests.module.MockModuleManager
import uk.co.reecedunn.intellij.plugin.core.tests.parser.ParsingTestCase
import uk.co.reecedunn.intellij.plugin.core.tests.roots.MockProjectRootsManager
import uk.co.reecedunn.intellij.plugin.core.vfs.ResourceVirtualFile
import uk.co.reecedunn.intellij.plugin.core.vfs.toPsiFile
import uk.co.reecedunn.intellij.plugin.xpm.psi.shadow.XpmShadowPsiElementFactory
import uk.co.reecedunn.intellij.plugin.xslt.lang.XSLT
import uk.co.reecedunn.intellij.plugin.xslt.psi.impl.XsltShadowPsiElementFactory

@Suppress("MemberVisibilityCanBePrivate", "SameParameterValue")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ParserTestCase(vararg definitions: ParserDefinition) :
    ParsingTestCase<XmlFile>(null, *definitions),
    PluginDescriptorProvider {
    // region PluginDescriptorProvider

    override val pluginDescriptor: PluginDescriptor
        get() = DefaultPluginDescriptor(pluginId, this::class.java.classLoader)

    override val pluginDisposable: Disposable
        get() = testRootDisposable

    // endregion

    @BeforeAll
    override fun setUp() {
        super.setUp()
        addExplicitExtension(LanguageASTFactory.INSTANCE, XMLLanguage.INSTANCE, XmlASTFactory())

        registerExtensionPoint(StartTagEndTokenProvider.EP_NAME, StartTagEndTokenProvider::class.java)
        registerExtensionPoint(XmlExtension.EP_NAME, XmlExtension::class.java)

        project.registerServiceInstance(ProjectRootManager::class.java, MockProjectRootsManager())
        project.registerServiceInstance(ModuleManager::class.java, MockModuleManager(project))
        project.registerServiceInstance(InjectedLanguageManager::class.java, MockInjectedLanguageManager())

        XpmShadowPsiElementFactory.register(this, XsltShadowPsiElementFactory)
    }

    @AfterAll
    override fun tearDown() {
        super.tearDown()
    }

    fun parseXml(resource: String): XmlFile {
        if (resource.endsWith(".xsl")) {
            val file = ResourceVirtualFile.create(this::class.java.classLoader, resource)
            return file.toPsiFile(project) as XmlFile
        }
        return super.parseText(resource)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> parse(resource: String, namespaceUri: String, localName: String): List<T> {
        return parseXml(resource).walkTree()
            .filterIsInstance<XmlTag>()
            .filter { it.namespace == namespaceUri && it.localName == localName }
            .map { XpmShadowPsiElementFactory.create(it) as T }
            .toList()
    }

    fun element(resource: String, localName: String): List<XmlTag> {
        return parseXml(resource).walkTree().filterIsInstance<XmlTag>().filter { e ->
            e.namespace == XSLT.NAMESPACE && e.localName == localName
        }.filterNotNull().toList()
    }

    fun attribute(resource: String, elementName: String, attributeName: String): List<XmlAttributeValue> {
        return element(resource, elementName).mapNotNull { e ->
            e.getAttribute(attributeName, "")?.valueElement
        }
    }
}
