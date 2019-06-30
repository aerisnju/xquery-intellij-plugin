/*
 * Copyright (C) 2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.completion.providers

import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import uk.co.reecedunn.intellij.plugin.core.completion.CompletionProviderEx
import uk.co.reecedunn.intellij.plugin.intellij.resources.XQueryIcons
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathVariableBinding
import uk.co.reecedunn.intellij.plugin.xpath.model.XsQNameValue
import uk.co.reecedunn.intellij.plugin.xquery.model.inScopeVariables

fun createVariableLookup(localName: String, prefix: String?, element: PsiElement?): LookupElementBuilder {
    return LookupElementBuilder.create(prefix?.let { "$it:$localName" } ?: localName)
        .withIcon(XQueryIcons.Nodes.VarDecl)
        .withPsiElement(element)
}

object XQueryVarRefProvider : CompletionProviderEx {
    override fun apply(element: PsiElement, context: ProcessingContext, result: CompletionResultSet) {
        val varRef = element.parent as XsQNameValue
        if (varRef.prefix == null || varRef.prefix?.element === element) {
            element.inScopeVariables().forEach { variable ->
                val localName = variable.variableName?.localName?.data ?: return@forEach
                val prefix = variable.variableName?.prefix?.data
                if (variable is XPathVariableBinding) {
                    result.addElement(createVariableLookup(localName, prefix, variable.variableName?.element))
                }
            }
        }
    }
}
