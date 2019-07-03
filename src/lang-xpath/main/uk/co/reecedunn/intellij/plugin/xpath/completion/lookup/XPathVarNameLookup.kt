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
package uk.co.reecedunn.intellij.plugin.xpath.completion.lookup

import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.intellij.resources.XPathIcons
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathVariableDefinition
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathVariableType

class XPathVarNameLookup(localName: String, prefix: String?, private val variable: XPathVariableDefinition) :
    XPathLookupElement(prefix?.let { "$it:$localName" } ?: localName) {

    override fun getObject(): Any = variable
    override fun getPsiElement(): PsiElement? = variable.variableName?.element

    private val presentation = LookupElementPresentation()
    init {
        presentation.itemText = lookupString
        presentation.icon = XPathIcons.Nodes.VarDecl
        presentation.typeText = (variable as? XPathVariableType)?.variableType?.typeName
    }

    override fun renderElement(presentation: LookupElementPresentation?) {
        presentation?.copyFrom(this.presentation)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is XPathVarNameLookup) return false
        return lookupString == other.lookupString && variable === other.variable
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + lookupString.hashCode()
        result = 31 * result + `object`.hashCode()
        return result
    }
}
