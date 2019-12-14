/*
 * Copyright (C) 2016-2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.tests.parser

import com.intellij.lang.LanguageASTFactory
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.roots.ProjectRootManager
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import uk.co.reecedunn.intellij.plugin.core.tests.module.MockModuleManager
import uk.co.reecedunn.intellij.plugin.core.tests.parser.ParsingTestCase
import uk.co.reecedunn.intellij.plugin.core.tests.roots.MockProjectRootsManager
import uk.co.reecedunn.intellij.plugin.intellij.lang.XPath
import uk.co.reecedunn.intellij.plugin.intellij.lang.XQuery
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryModule
import uk.co.reecedunn.intellij.plugin.xquery.parser.XQueryASTFactory
import uk.co.reecedunn.intellij.plugin.xquery.parser.XQueryParserDefinition
import uk.co.reecedunn.intellij.plugin.intellij.settings.XQueryProjectSettings
import uk.co.reecedunn.intellij.plugin.xdm.model.ImportPathResolver
import uk.co.reecedunn.intellij.plugin.xdm.module.XdmModulePathFactory
import uk.co.reecedunn.intellij.plugin.xpath.parser.XPathASTFactory
import uk.co.reecedunn.intellij.plugin.xpath.parser.XPathParserDefinition

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ParserTestCase :
    ParsingTestCase<XQueryModule>("xqy", XQueryParserDefinition(), XPathParserDefinition()) {

    open fun registerModules(manager: MockModuleManager) {}

    @BeforeAll
    override fun setUp() {
        super.setUp()
        myProject.registerService(XQueryProjectSettings::class.java, XQueryProjectSettings())
        addExplicitExtension(LanguageASTFactory.INSTANCE, XPath, XPathASTFactory())
        addExplicitExtension(LanguageASTFactory.INSTANCE, XQuery, XQueryASTFactory())
        myProject.registerService(ProjectRootManager::class.java, MockProjectRootsManager())

        val manager = MockModuleManager(myProject)
        registerModules(manager)
        myProject.registerService(ModuleManager::class.java, manager)

        registerExtensionPoint(XdmModulePathFactory.EP_NAME, XdmModulePathFactory::class.java)
        registerModulePathFactory(uk.co.reecedunn.intellij.plugin.xdm.java.JavaModulePath)

        registerExtensionPoint(ImportPathResolver.IMPORT_PATH_RESOLVER_EP, ImportPathResolver::class.java)
        registerBuiltInFunctions(uk.co.reecedunn.intellij.plugin.basex.model.BuiltInFunctions)
        registerBuiltInFunctions(uk.co.reecedunn.intellij.plugin.marklogic.model.BuiltInFunctions)
        registerBuiltInFunctions(uk.co.reecedunn.intellij.plugin.saxon.model.BuiltInFunctions)
        registerBuiltInFunctions(uk.co.reecedunn.intellij.plugin.w3.model.BuiltInFunctions)
    }

    @AfterAll
    override fun tearDown() {
        super.tearDown()
    }

    private fun registerModulePathFactory(factory: XdmModulePathFactory) {
        registerExtension(XdmModulePathFactory.EP_NAME, factory)
    }

    private fun registerBuiltInFunctions(resolver: ImportPathResolver) {
        registerExtension(ImportPathResolver.IMPORT_PATH_RESOLVER_EP, resolver)
    }

    protected val settings get(): XQueryProjectSettings = XQueryProjectSettings.getInstance(myProject)
}
