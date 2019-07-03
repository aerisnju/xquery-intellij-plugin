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

import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.intellij.resources.XPathIcons
import uk.co.reecedunn.intellij.plugin.xpath.completion.XPathEmptyFunctionInsertHandler
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathFunctionDeclaration

class XPathFunctionCallLookup(localName: String, prefix: String?, private val function: XPathFunctionDeclaration) :
    XPathLookupElement(prefix?.let { "$it:$localName" } ?: localName) {
    init {
        presentation.icon = XPathIcons.Nodes.FunctionDecl
        presentation.tailText = function.paramListPresentation?.presentableText ?: "()"
        presentation.typeText = function.returnType?.typeName
    }

    override fun getObject(): Any = function
    override fun getPsiElement(): PsiElement? = function.functionName?.element

    override fun handleInsert(context: InsertionContext) {
        XPathEmptyFunctionInsertHandler.handleInsert(context, this)
    }
}
