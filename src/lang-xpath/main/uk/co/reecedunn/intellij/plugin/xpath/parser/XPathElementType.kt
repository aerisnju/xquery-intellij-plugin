/*
 * Copyright (C) 2018 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xpath.parser

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import uk.co.reecedunn.intellij.plugin.core.parser.ICompositeElementType
import uk.co.reecedunn.intellij.plugin.intellij.lang.XPath
import uk.co.reecedunn.intellij.plugin.xpath.psi.impl.plugin.PluginAnyItemTypePsiImpl
import uk.co.reecedunn.intellij.plugin.xpath.psi.impl.plugin.PluginAnyTextTestPsiImpl
import uk.co.reecedunn.intellij.plugin.xpath.psi.impl.plugin.PluginQuantifiedExprBindingPsiImpl
import uk.co.reecedunn.intellij.plugin.xpath.psi.impl.plugin.PluginWildcardIndicatorPsiImpl
import uk.co.reecedunn.intellij.plugin.xpath.psi.impl.xpath.*

object XPathElementType {
    // region XPath 1.0

    val XPATH = IFileElementType(XPath)

    val AND_EXPR: IElementType = ICompositeElementType(
        "XQUERY_AND_EXPR",
        XPathAndExprPsiImpl::class.java,
        XPath
    )

    val ADDITIVE_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ADDITIVE_EXPR",
        XPathAdditiveExprPsiImpl::class.java,
        XPath
    )

    val AXIS_STEP: IElementType = ICompositeElementType(
        "XQUERY_AXIS_STEP",
        XPathAxisStepPsiImpl::class.java,
        XPath
    )

    val ABBREV_FORWARD_STEP: IElementType = ICompositeElementType(
        "XQUERY_ABBREV_FORWARD_STEP",
        XPathAbbrevForwardStepPsiImpl::class.java,
        XPath
    )

    val ABBREV_REVERSE_STEP: IElementType = ICompositeElementType(
        "XQUERY_ABBREV_REVERSE_STEP",
        XPathAbbrevReverseStepPsiImpl::class.java,
        XPath
    )

    val ANY_KIND_TEST: IElementType = ICompositeElementType(
        "XQUERY_ANY_KIND_TEST",
        XPathAnyKindTestPsiImpl::class.java,
        XPath
    )

    val COMMENT_TEST: IElementType = ICompositeElementType(
        "XQUERY_COMMENT_TEST",
        XPathCommentTestPsiImpl::class.java,
        XPath
    )

    // endregion
    // region XPath 2.0

    val CASTABLE_EXPR: IElementType = ICompositeElementType(
        "XQUERY_CASTABLE_EXPR",
        XPathCastableExprPsiImpl::class.java,
        XPath
    )

    val CAST_EXPR: IElementType = ICompositeElementType(
        "XQUERY_CAST_EXPR",
        XPathCastExprPsiImpl::class.java,
        XPath
    )

    val ATTRIBUTE_TEST: IElementType = ICompositeElementType(
        "XQUERY_ATTRIBUTE_TEST",
        XPathAttributeTestPsiImpl::class.java,
        XPath
    )

    val COMMENT: IElementType = ICompositeElementType(
        "XQUERY_COMMENT",
        XPathCommentPsiImpl::class.java,
        XPath
    )

    // endregion
    // region XPath 3.0

    val ARGUMENT_LIST: IElementType = ICompositeElementType(
        "XQUERY_ARGUMENT_LIST",
        XPathArgumentListPsiImpl::class.java,
        XPath
    )

    val ARGUMENT: IElementType = ICompositeElementType(
        "XQUERY_ARGUMENT",
        XPathArgumentPsiImpl::class.java,
        XPath
    )

    val ARGUMENT_PLACEHOLDER: IElementType = ICompositeElementType(
        "XQUERY_ARGUMENT_PLACEHOLDER",
        XPathArgumentPlaceholderPsiImpl::class.java,
        XPath
    )

    val ATOMIC_OR_UNION_TYPE: IElementType = ICompositeElementType(
        "XQUERY_ATOMIC_OR_UNION_TYPE",
        XPathAtomicOrUnionTypePsiImpl::class.java,
        XPath
    )

    // endregion
    // region XPath 3.1

    val ARROW_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ARROW_EXPR",
        XPathArrowExprPsiImpl::class.java,
        XPath
    )

    val ARROW_FUNCTION_SPECIFIER: IElementType = ICompositeElementType(
        "XQUERY_ARROW_FUNCTION_SPECIFIER",
        XPathArrowFunctionSpecifierPsiImpl::class.java,
        XPath
    )

    val ANY_MAP_TEST: IElementType = ICompositeElementType(
        "XQUERY_ANY_MAP_TEST",
        XPathAnyMapTestPsiImpl::class.java,
        XPath
    )

    val ANY_ARRAY_TEST: IElementType = ICompositeElementType(
        "XQUERY_ANY_ARRAY_TEST",
        XPathAnyArrayTestPsiImpl::class.java,
        XPath
    )

    // endregion
    // region XQuery IntelliJ Plugin

    val QUANTIFIED_EXPR_BINDING: IElementType = ICompositeElementType(
        "XQUERY_QUANTIFIED_EXPR_BINDING",
        PluginQuantifiedExprBindingPsiImpl::class.java,
        XPath
    )

    val ANY_TEXT_TEST: IElementType = ICompositeElementType(
        "XQUERY_ANY_TEXT_TEST",
        PluginAnyTextTestPsiImpl::class.java,
        XPath
    )

    val WILDCARD_INDICATOR: IElementType = ICompositeElementType(
        "XQUERY_WILDCARD_INDICATOR",
        PluginWildcardIndicatorPsiImpl::class.java,
        XPath
    )

    val ANY_ITEM_TYPE: IElementType = ICompositeElementType(
        "XQUERY_ANY_ITEM_TYPE",
        PluginAnyItemTypePsiImpl::class.java,
        XPath
    )

    // endregion
}
