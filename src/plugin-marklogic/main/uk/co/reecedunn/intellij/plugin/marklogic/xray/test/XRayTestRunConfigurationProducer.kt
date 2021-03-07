/*
 * Copyright (C) 2021 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.marklogic.xray.test

import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.marklogic.intellij.execution.configurations.type.XRayTestConfigurationType
import uk.co.reecedunn.intellij.plugin.marklogic.xray.configuration.XRayTestConfiguration
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathEQName

class XRayTestRunConfigurationProducer : LazyRunConfigurationProducer<XRayTestConfiguration>() {
    override fun getConfigurationFactory(): ConfigurationFactory {
        val type = ConfigurationTypeUtil.findConfigurationType(XRayTestConfigurationType::class.java)
        return type.configurationFactories[0]
    }

    override fun isConfigurationFromContext(
        configuration: XRayTestConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val name = context.location?.psiElement as? XPathEQName ?: return false
        return when {
            XRayTestService.isTestModule(name) -> configuration.appliesTo(name.containingFile)
            XRayTestService.isTestCase(name) -> configuration.appliesTo(name.containingFile, name)
            else -> false
        }
    }

    override fun setupConfigurationFromContext(
        configuration: XRayTestConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val name = context.location?.psiElement as? XPathEQName ?: return false
        return when {
            XRayTestService.isTestModule(name) -> configuration.create(name.containingFile)
            XRayTestService.isTestCase(name) -> configuration.create(name.containingFile, name)
            else -> false
        }
    }
}
