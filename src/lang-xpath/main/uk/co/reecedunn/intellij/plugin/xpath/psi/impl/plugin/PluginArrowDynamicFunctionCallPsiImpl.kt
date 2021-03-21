/*
 * Copyright (C) 2019-2021 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xpath.psi.impl.plugin

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.core.data.CacheableProperty
import uk.co.reecedunn.intellij.plugin.core.sequences.children
import uk.co.reecedunn.intellij.plugin.core.sequences.reverse
import uk.co.reecedunn.intellij.plugin.core.sequences.siblings
import uk.co.reecedunn.intellij.plugin.xpath.ast.filterNotWhitespace
import uk.co.reecedunn.intellij.plugin.xpath.ast.plugin.PluginArrowDynamicFunctionCall
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathArgumentList
import uk.co.reecedunn.intellij.plugin.xpm.lang.validation.XpmSyntaxValidationElement
import uk.co.reecedunn.intellij.plugin.xpm.optree.expr.XpmExpression
import uk.co.reecedunn.intellij.plugin.xpm.optree.map.XpmMapEntry

class PluginArrowDynamicFunctionCallPsiImpl(node: ASTNode) :
    ASTWrapperPsiElement(node),
    PluginArrowDynamicFunctionCall,
    XpmSyntaxValidationElement {
    // region PsiElement

    override fun subtreeChanged() {
        super.subtreeChanged()
        cachedPositionalArguments.invalidate()
    }

    // endregion
    // region XpmExpression

    override val expressionElement: XPathArgumentList
        get() = children().filterIsInstance<XPathArgumentList>().first()

    // endregion
    // region XpmArrowFunctionCall

    override val functionCallExpression: XpmExpression?
        get() = children().filterIsInstance<XpmExpression>().firstOrNull()

    private val cachedPositionalArguments = CacheableProperty {
        val argumentList = children().filterIsInstance<XPathArgumentList>().first()
        argumentList.children().filterIsInstance<XpmExpression>().toList()
    }

    override val positionalArguments: List<XpmExpression>
        get() = cachedPositionalArguments.get()!!

    override val keywordArguments: List<XpmMapEntry> = listOf()

    override val sourceExpression: XpmExpression?
        get() = reverse(siblings()).filterIsInstance<XpmExpression>().firstOrNull()

    // endregion
    // region XpmSyntaxValidationElement

    override val conformanceElement: PsiElement
        get() = reverse(siblings()).filterNotWhitespace().firstOrNull() ?: firstChild

    // endregion
}
