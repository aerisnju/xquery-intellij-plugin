/*
 * Copyright (C) 2016-2021 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xpath.psi.impl.xpath

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.core.data.CacheableProperty
import uk.co.reecedunn.intellij.plugin.core.sequences.children
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathArgumentList
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathFunctionCall
import uk.co.reecedunn.intellij.plugin.xdm.types.XsQNameValue
import uk.co.reecedunn.intellij.plugin.xpm.optree.XpmExpression

class XPathFunctionCallPsiImpl(node: ASTNode) : ASTWrapperPsiElement(node), XPathFunctionCall {
    // region PsiElement

    override fun subtreeChanged() {
        super.subtreeChanged()
        cachedPositionalArguments.invalidate()
    }

    // endregion
    // region XpmExpression

    override val expressionElement: PsiElement
        get() {
            val argumentList = argumentList
            return when {
                argumentList.isPartialFunctionApplication -> argumentList
                else -> this
            }
        }

    // endregion
    // region XpmFunctionReference

    override val argumentList: XPathArgumentList
        get() = children().filterIsInstance<XPathArgumentList>().first()

    override val functionName: XsQNameValue?
        get() = firstChild as? XsQNameValue

    override val arity: Int
        get() = argumentList.arity

    // endregion
    // region XpmFunctionCall

    private val cachedPositionalArguments = CacheableProperty {
        argumentList.children().filterIsInstance<XpmExpression>().toList()
    }

    override val positionalArguments: List<XpmExpression>
        get() = cachedPositionalArguments.get()!!

    // endregion
}
