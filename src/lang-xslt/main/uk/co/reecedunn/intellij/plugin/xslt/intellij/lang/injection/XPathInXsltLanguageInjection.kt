/*
 * Copyright (C) 2018-2020 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xslt.intellij.lang.injection

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlFile
import uk.co.reecedunn.intellij.plugin.xpath.intellij.lang.XPath
import uk.co.reecedunn.intellij.plugin.intellij.lang.XPathSubset
import uk.co.reecedunn.intellij.plugin.xslt.completion.xpath.property.XPathSyntaxSubset
import uk.co.reecedunn.intellij.plugin.xslt.dom.isIntellijXPathPluginEnabled
import uk.co.reecedunn.intellij.plugin.xslt.dom.isXslt

class XPathInXsltLanguageInjection : MultiHostInjector {
    override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
        return mutableListOf(XmlAttributeValue::class.java)
    }

    override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
        if (isIntellijXPathPluginEnabled() || (context as? XmlFile)?.isXslt() == false)
            return

        when (XPathSyntaxSubset.get(context)) {
            XPathSubset.XPath, XPathSubset.XsltPattern -> {
                val host = context as PsiLanguageInjectionHost
                val range = host.textRange

                registrar.startInjecting(XPath)
                registrar.addPlace(null, null, host, range.shiftLeft(range.startOffset))
                registrar.doneInjecting()
            }
            XPathSubset.Unknown -> {
            }
        }
    }
}