/*
 * Copyright (C) 2018-2019 Reece H. Dunn
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

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import uk.co.reecedunn.intellij.plugin.core.lang.errorOnTokenType
import uk.co.reecedunn.intellij.plugin.core.lang.matchTokenType
import uk.co.reecedunn.intellij.plugin.core.lang.matchTokenTypeWithMarker
import uk.co.reecedunn.intellij.plugin.intellij.resources.XPathBundle
import uk.co.reecedunn.intellij.plugin.xpath.lexer.INCNameType
import uk.co.reecedunn.intellij.plugin.xpath.lexer.XPathTokenType

/**
 * A unified XPath parser for different XPath versions and dialects.
 */
@Suppress("PropertyName", "PrivatePropertyName")
open class XPathParser : PsiParser {
    // region XPath/XQuery Element Types
    //
    // These element types have different PSI implementations in XPath and XQuery.

    open val ENCLOSED_EXPR: IElementType = XPathElementType.ENCLOSED_EXPR
    open val EXPR: IElementType = XPathElementType.EXPR
    open val FUNCTION_BODY: IElementType = XPathElementType.FUNCTION_BODY
    open val FUNCTION_TEST: IElementType = XPathElementType.FUNCTION_TEST

    // endregion
    // region PsiParser

    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val rootMarker = builder.mark()
        parse(builder)
        rootMarker.done(root)
        return builder.treeBuilt
    }

    // endregion
    // region Grammar

    fun parse(builder: PsiBuilder) {
        var matched = false
        var haveError = false
        while (builder.tokenType != null) {
            if (matched && !haveError) {
                builder.error(XPathBundle.message("parser.error.expected-eof"))
                haveError = true
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (parse(builder, !matched && !haveError)) {
                matched = true
                continue
            }

            if (haveError) {
                builder.advanceLexer()
            } else if (builder.tokenType != null) {
                val errorMarker = builder.mark()
                builder.advanceLexer()
                errorMarker.error(XPathBundle.message("parser.error.unexpected-token"))
                haveError = true
            }
        }
    }

    open fun parse(builder: PsiBuilder, isFirst: Boolean): Boolean {
        if (parseExpr(builder, null)) {
            return true
        }
        if (isFirst) {
            builder.error(XPathBundle.message("parser.error.expected-expression"))
        }
        return false
    }

    // endregion
    // region Grammar :: Expr

    open fun parseExpr(builder: PsiBuilder, type: IElementType?, functionDeclRecovery: Boolean = false): Boolean {
        val marker = builder.mark()
        if (parseExprSingle(builder)) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            while (builder.matchTokenType(XPathTokenType.COMMA)) {
                parseWhiteSpaceAndCommentTokens(builder)
                if (!parseExprSingle(builder) && !haveErrors) {
                    builder.error(XPathBundle.message("parser.error.expected-expression"))
                    haveErrors = true
                }
                parseWhiteSpaceAndCommentTokens(builder)
            }

            if (type == null)
                marker.drop()
            else
                marker.done(type)
            return true
        }
        marker.drop()
        return false
    }

    open fun parseExprSingle(builder: PsiBuilder): Boolean {
        return parseOrExpr(builder, null)
    }

    // endregion
    // region Grammar :: Expr :: OrExpr

    fun parseOrExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parseAndExpr(builder, type)) {
            parseWhiteSpaceAndCommentTokens(builder)
            while (builder.matchTokenType(XPathTokenType.OR_EXPR_TOKENS)) {
                parseWhiteSpaceAndCommentTokens(builder)
                if (!parseAndExpr(builder, type)) {
                    builder.error(XPathBundle.message("parser.error.expected", "AndExpr"))
                }
            }

            marker.done(XPathElementType.OR_EXPR)
            return true
        }
        marker.drop()
        return false
    }

    open fun parseAndExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        return parseUnaryExpr(builder, type)
    }

    fun parseUnaryExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        var matched = false
        while (builder.matchTokenType(XPathTokenType.UNARY_EXPR_TOKENS)) {
            parseWhiteSpaceAndCommentTokens(builder)
            matched = true
        }
        if (matched) {
            if (parseValueExpr(builder, null)) {
                marker.done(XPathElementType.UNARY_EXPR)
                return true
            } else if (matched) {
                builder.error(XPathBundle.message("parser.error.expected", "ValueExpr"))
                marker.done(XPathElementType.UNARY_EXPR)
                return true
            }
        } else if (parseValueExpr(builder, type)) {
            marker.drop()
            return true
        }
        marker.drop()
        return false
    }

    // endregion
    // region Grammar :: Expr :: OrExpr :: ValueExpr

    open fun parseValueExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        return parsePathExpr(builder, type)
    }

    fun parsePathExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (builder.matchTokenType(XPathTokenType.DIRECT_DESCENDANTS_PATH)) {
            parseWhiteSpaceAndCommentTokens(builder)
            parseRelativePathExpr(builder, null)

            marker.done(XPathElementType.PATH_EXPR)
            return true
        } else if (builder.matchTokenType(XPathTokenType.ALL_DESCENDANTS_PATH)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseRelativePathExpr(builder, null)) {
                builder.error(XPathBundle.message("parser.error.expected", "RelativePathExpr"))
            }

            marker.done(XPathElementType.PATH_EXPR)
            return true
        } else if (parseRelativePathExpr(builder, type)) {
            marker.drop()
            return true
        }
        marker.drop()
        return false
    }

    private fun parseRelativePathExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parseStepExpr(builder, type)) {
            parseWhiteSpaceAndCommentTokens(builder)
            var haveRelativePathExpr = false
            while (builder.matchTokenType(XPathTokenType.RELATIVE_PATH_EXPR_TOKENS)) {
                parseWhiteSpaceAndCommentTokens(builder)
                if (!parseStepExpr(builder, null)) {
                    builder.error(XPathBundle.message("parser.error.expected", "StepExpr"))
                }

                parseWhiteSpaceAndCommentTokens(builder)
                haveRelativePathExpr = true
            }

            if (haveRelativePathExpr)
                marker.done(XPathElementType.RELATIVE_PATH_EXPR)
            else
                marker.drop()
            return true
        }
        marker.drop()
        return false
    }

    // endregion
    // region Grammar :: Expr :: OrExpr :: StepExpr

    open fun parseStepExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        return parseAxisStep(builder, type) || parsePostfixExpr(builder, type)
    }

    fun parseAxisStep(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parseReverseStep(builder) || parseForwardStep(builder, type)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (parsePredicateList(builder))
                marker.done(XPathElementType.AXIS_STEP)
            else
                marker.drop()
            return true
        }

        marker.drop()
        return false
    }

    private fun parseForwardStep(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parseForwardAxis(builder)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseNodeTest(builder, null)) {
                builder.error(XPathBundle.message("parser.error.expected", "NodeTest"))
            }

            marker.done(XPathElementType.FORWARD_STEP)
            return true
        } else if (parseAbbrevForwardStep(builder, type)) {
            marker.drop()
            return true
        }

        marker.drop()
        return false
    }

    private fun parseForwardAxis(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        if (builder.matchTokenType(XPathTokenType.FORWARD_AXIS_TOKENS)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.AXIS_SEPARATOR)) {
                marker.rollbackTo()
                return false
            }

            marker.done(XPathElementType.FORWARD_AXIS)
            return true
        }
        marker.drop()
        return false
    }

    private fun parseAbbrevForwardStep(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        val matched = builder.matchTokenType(XPathTokenType.ATTRIBUTE_SELECTOR)

        parseWhiteSpaceAndCommentTokens(builder)
        if (parseNodeTest(builder, type)) {
            if (matched)
                marker.done(XPathElementType.ABBREV_FORWARD_STEP)
            else
                marker.drop()
            return true
        } else if (matched) {
            builder.error(XPathBundle.message("parser.error.expected", "NodeTest"))

            marker.done(XPathElementType.ABBREV_FORWARD_STEP)
            return true
        }
        marker.drop()
        return false
    }

    private fun parseReverseStep(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        if (parseReverseAxis(builder)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseNodeTest(builder, null)) {
                builder.error(XPathBundle.message("parser.error.expected", "NodeTest"))
            }

            marker.done(XPathElementType.REVERSE_STEP)
            return true
        } else if (parseAbbrevReverseStep(builder)) {
            marker.drop()
            return true
        }

        marker.drop()
        return false
    }

    private fun parseReverseAxis(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        if (builder.matchTokenType(XPathTokenType.REVERSE_AXIS_TOKENS)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.AXIS_SEPARATOR)) {
                marker.rollbackTo()
                return false
            }

            marker.done(XPathElementType.REVERSE_AXIS)
            return true
        }
        marker.drop()
        return false
    }

    private fun parseAbbrevReverseStep(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.PARENT_SELECTOR)
        if (marker != null) {
            marker.done(XPathElementType.ABBREV_REVERSE_STEP)
            return true
        }
        return false
    }

    private fun parseNodeTest(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parseKindTest(builder) || parseNameTest(builder, type)) {
            marker.done(XPathElementType.NODE_TEST)
            return true
        }
        marker.drop()
        return false
    }

    fun parseNameTest(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (
            parseEQNameOrWildcard(builder, XPathElementType.WILDCARD, type === XPathElementType.MAP_CONSTRUCTOR_ENTRY)
        ) {
            val nonNameMarker = builder.mark()
            parseWhiteSpaceAndCommentTokens(builder)
            val nextTokenType = builder.tokenType
            nonNameMarker.rollbackTo()

            if (nextTokenType === XPathTokenType.PARENTHESIS_OPEN) {
                marker.rollbackTo()
                return false
            }
            marker.done(XPathElementType.NAME_TEST)
            return true
        }
        marker.drop()
        return false
    }

    open fun parsePostfixExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.mark()
        if (parsePrimaryExpr(builder, type)) {
            parseWhiteSpaceAndCommentTokens(builder)
            var havePostfixExpr = false
            while (parsePredicate(builder)) {
                parseWhiteSpaceAndCommentTokens(builder)
                havePostfixExpr = true
            }

            if (havePostfixExpr)
                marker.done(XPathElementType.POSTFIX_EXPR)
            else
                marker.drop()
            return true
        }
        marker.drop()
        return false
    }

    fun parsePredicateList(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        var havePredicate = false
        while (parsePredicate(builder)) {
            parseWhiteSpaceAndCommentTokens(builder)
            havePredicate = true
        }
        if (havePredicate)
            marker.done(XPathElementType.PREDICATE_LIST)
        else
            marker.drop()
        return havePredicate
    }

    fun parsePredicate(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.SQUARE_OPEN)
        if (marker != null) {
            var haveErrors = false
            parseWhiteSpaceAndCommentTokens(builder)

            if (!parseExpr(builder, EXPR)) {
                builder.error(XPathBundle.message("parser.error.expected-expression"))
                haveErrors = true
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.SQUARE_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", "]"))
            }

            marker.done(XPathElementType.PREDICATE)
            return true
        }

        return false
    }

    // endregion
    // region Grammar :: Expr :: OrExpr :: PrimaryExpr

    @Suppress("Reformat") // Kotlin formatter bug: https://youtrack.jetbrains.com/issue/KT-22518
    open fun parsePrimaryExpr(builder: PsiBuilder, type: IElementType?): Boolean {
        return (
            parseLiteral(builder) ||
            parseVarRef(builder, type) ||
            parseParenthesizedExpr(builder) ||
            parseContextItemExpr(builder) ||
            parseFunctionCall(builder)
        )
    }

    fun parseLiteral(builder: PsiBuilder): Boolean {
        return parseNumericLiteral(builder) || parseStringLiteral(builder)
    }

    private fun parseNumericLiteral(builder: PsiBuilder): Boolean {
        if (
            builder.matchTokenType(XPathTokenType.INTEGER_LITERAL) ||
            builder.matchTokenType(XPathTokenType.DOUBLE_LITERAL)
        ) {
            return true
        } else if (builder.matchTokenType(XPathTokenType.DECIMAL_LITERAL)) {
            builder.errorOnTokenType(
                XPathTokenType.PARTIAL_DOUBLE_LITERAL_EXPONENT,
                XPathBundle.message("parser.error.incomplete-double-exponent")
            )
            return true
        }
        return false
    }

    fun parseVarRef(builder: PsiBuilder, type: IElementType?): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.VARIABLE_INDICATOR)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (
                !parseEQNameOrWildcard(
                    builder, XPathElementType.VAR_NAME,
                    type === XPathElementType.MAP_CONSTRUCTOR_ENTRY
                )
            ) {
                builder.error(XPathBundle.message("parser.error.expected-eqname"))
            }

            marker.done(XPathElementType.VAR_REF)
            return true
        }
        return false
    }

    fun parseParenthesizedExpr(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.PARENTHESIS_OPEN)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (parseExpr(builder, EXPR)) {
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.PARENTHESIZED_EXPR)
            return true
        }
        return false
    }

    private fun parseContextItemExpr(builder: PsiBuilder): Boolean {
        return builder.matchTokenType(XPathTokenType.DOT)
    }

    open fun parseFunctionCall(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        if (parseEQNameOrWildcard(builder, QNAME, false)) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseArgumentList(builder)) {
                marker.rollbackTo()
                return false
            }
            marker.done(XPathElementType.FUNCTION_CALL)
            return true
        }
        marker.drop()
        return false
    }

    fun parseArgumentList(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.PARENTHESIS_OPEN)
        if (marker != null) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            if (parseArgument(builder)) {
                parseWhiteSpaceAndCommentTokens(builder)
                while (builder.matchTokenType(XPathTokenType.COMMA)) {
                    parseWhiteSpaceAndCommentTokens(builder)
                    if (!parseArgument(builder) && !haveErrors) {
                        builder.error(XPathBundle.message("parser.error.expected-either", "ExprSingle", "?"))
                        haveErrors = true
                    }

                    parseWhiteSpaceAndCommentTokens(builder)
                }
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.ARGUMENT_LIST)
            return true
        }
        return false
    }

    private fun parseArgument(builder: PsiBuilder): Boolean {
        val marker = builder.mark()
        if (parseExprSingle(builder)) {
            marker.done(XPathElementType.ARGUMENT)
            return true
        }
        marker.drop()
        return parseArgumentPlaceholder(builder)
    }

    private fun parseArgumentPlaceholder(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.OPTIONAL)
        if (marker != null) {
            marker.done(XPathElementType.ARGUMENT_PLACEHOLDER)
            return true
        }
        return false
    }

    // endregion
    // region Grammar :: TypeDeclaration :: KindTest

    private val ATTRIBUTE_DECLARATION: IElementType get() = QNAME
    private val ATTRIBUTE_NAME: IElementType get() = QNAME

    private val ELEMENT_DECLARATION: IElementType get() = QNAME
    private val ELEMENT_NAME: IElementType get() = QNAME

    @Suppress("Reformat") // Kotlin formatter bug: https://youtrack.jetbrains.com/issue/KT-22518
    open fun parseKindTest(builder: PsiBuilder): Boolean {
        return (
            parseDocumentTest(builder) ||
            parseElementTest(builder) ||
            parseAttributeTest(builder) ||
            parseSchemaElementTest(builder) ||
            parseSchemaAttributeTest(builder) ||
            parsePITest(builder) ||
            parseCommentTest(builder) ||
            parseTextTest(builder) ||
            parseNamespaceNodeTest(builder) ||
            parseAnyKindTest(builder)
        )
    }

    open fun parseAnyKindTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_NODE)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.ANY_KIND_TEST)
            return true
        }
        return false
    }

    open fun parseDocumentTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_DOCUMENT_NODE)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (
                parseElementTest(builder) ||
                parseSchemaElementTest(builder)
            ) {
                //
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.DOCUMENT_TEST)
            return true
        }
        return false
    }

    open fun parseTextTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_TEXT)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.ANY_TEXT_TEST)
            return true
        }
        return false
    }

    private fun parseCommentTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_COMMENT)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.COMMENT_TEST)
            return true
        }
        return false
    }

    private fun parseNamespaceNodeTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_NAMESPACE_NODE)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.NAMESPACE_NODE_TEST)
            return true
        }
        return false
    }

    private fun parsePITest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_PROCESSING_INSTRUCTION)
        if (marker != null) {
            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (parseQNameOrWildcard(builder, NCNAME) || parseStringLiteral(builder)) {
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE)) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.PI_TEST)
            return true
        }
        return false
    }

    open fun parseAttributeTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_ATTRIBUTE)
        if (marker != null) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (parseAttribNameOrWildcard(builder)) {
                parseWhiteSpaceAndCommentTokens(builder)
                if (builder.matchTokenType(XPathTokenType.COMMA)) {
                    parseWhiteSpaceAndCommentTokens(builder)
                    if (!parseEQNameOrWildcard(builder, XPathElementType.TYPE_NAME, false)) {
                        builder.error(XPathBundle.message("parser.error.expected-eqname"))
                        haveErrors = true
                    }
                } else if (builder.tokenType !== XPathTokenType.PARENTHESIS_CLOSE) {
                    builder.error(XPathBundle.message("parser.error.expected", ","))
                    haveErrors = true
                    parseEQNameOrWildcard(builder, XPathElementType.TYPE_NAME, false)
                }
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.ATTRIBUTE_TEST)
            return true
        }
        return false
    }

    @Suppress("Reformat") // Kotlin formatter bug: https://youtrack.jetbrains.com/issue/KT-22518
    fun parseAttribNameOrWildcard(builder: PsiBuilder): Boolean {
        return (
            builder.matchTokenType(XPathTokenType.STAR) ||
            parseEQNameOrWildcard(builder, ATTRIBUTE_NAME, false)
        )
    }

    private fun parseSchemaAttributeTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_SCHEMA_ATTRIBUTE)
        if (marker != null) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseEQNameOrWildcard(builder, ATTRIBUTE_DECLARATION, false)) {
                builder.error(XPathBundle.message("parser.error.expected-eqname"))
                haveErrors = true
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.SCHEMA_ATTRIBUTE_TEST)
            return true
        }
        return false
    }

    open fun parseElementTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_ELEMENT)
        if (marker != null) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (parseElementNameOrWildcard(builder)) {
                parseWhiteSpaceAndCommentTokens(builder)
                if (builder.matchTokenType(XPathTokenType.COMMA)) {
                    parseWhiteSpaceAndCommentTokens(builder)
                    if (!parseEQNameOrWildcard(builder, XPathElementType.TYPE_NAME, false)) {
                        builder.error(XPathBundle.message("parser.error.expected-eqname"))
                        haveErrors = true
                    }

                    parseWhiteSpaceAndCommentTokens(builder)
                    builder.matchTokenType(XPathTokenType.OPTIONAL)
                } else if (builder.tokenType !== XPathTokenType.PARENTHESIS_CLOSE) {
                    builder.error(XPathBundle.message("parser.error.expected", ","))
                    haveErrors = true
                    parseEQNameOrWildcard(builder, XPathElementType.TYPE_NAME, false)
                }
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.ELEMENT_TEST)
            return true
        }
        return false
    }

    @Suppress("Reformat") // Kotlin formatter bug: https://youtrack.jetbrains.com/issue/KT-22518
    fun parseElementNameOrWildcard(builder: PsiBuilder): Boolean {
        return (
            builder.matchTokenType(XPathTokenType.STAR) ||
            parseEQNameOrWildcard(builder, ELEMENT_NAME, false)
        )
    }

    fun parseSchemaElementTest(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.K_SCHEMA_ELEMENT)
        if (marker != null) {
            var haveErrors = false

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_OPEN)) {
                marker.rollbackTo()
                return false
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!parseEQNameOrWildcard(builder, ELEMENT_DECLARATION, false)) {
                builder.error(XPathBundle.message("parser.error.expected-eqname"))
                haveErrors = true
            }

            parseWhiteSpaceAndCommentTokens(builder)
            if (!builder.matchTokenType(XPathTokenType.PARENTHESIS_CLOSE) && !haveErrors) {
                builder.error(XPathBundle.message("parser.error.expected", ")"))
            }

            marker.done(XPathElementType.SCHEMA_ELEMENT_TEST)
            return true
        }
        return false
    }

    // endregion
    // region Lexical Structure :: Terminal Symbols

    open val STRING_LITERAL: IElementType = XPathElementType.STRING_LITERAL

    fun parseStringLiteral(builder: PsiBuilder): Boolean {
        return parseStringLiteral(builder, STRING_LITERAL)
    }

    open fun parseStringLiteral(builder: PsiBuilder, type: IElementType): Boolean {
        val stringMarker = builder.matchTokenTypeWithMarker(XPathTokenType.STRING_LITERAL_START)
        while (stringMarker != null) {
            if (
                builder.matchTokenType(XPathTokenType.STRING_LITERAL_CONTENTS) ||
                builder.matchTokenType(XPathTokenType.ESCAPED_CHARACTER)
            ) {
                //
            } else if (builder.matchTokenType(XPathTokenType.STRING_LITERAL_END)) {
                stringMarker.done(type)
                return true
            } else {
                stringMarker.done(type)
                builder.error(XPathBundle.message("parser.error.incomplete-string"))
                return true
            }
        }
        return false
    }

    fun parseComment(builder: PsiBuilder): Boolean {
        if (builder.tokenType === XPathTokenType.COMMENT_START_TAG) {
            val commentMarker = builder.mark()
            builder.advanceLexer()
            // NOTE: XQueryTokenType.COMMENT is omitted by the PsiBuilder.
            if (builder.tokenType === XPathTokenType.COMMENT_END_TAG) {
                builder.advanceLexer()
                commentMarker.done(XPathElementType.COMMENT)
            } else {
                builder.advanceLexer() // XQueryTokenType.UNEXPECTED_END_OF_BLOCK
                commentMarker.done(XPathElementType.COMMENT)
                builder.error(XPathBundle.message("parser.error.incomplete-comment"))
            }
            return true
        } else if (builder.tokenType === XPathTokenType.COMMENT_END_TAG) {
            val errorMarker = builder.mark()
            builder.advanceLexer()
            errorMarker.error(XPathBundle.message("parser.error.end-of-comment-without-start", "(:"))
            return true
        }
        return false
    }

    open fun parseWhiteSpaceAndCommentTokens(builder: PsiBuilder): Boolean {
        var skipped = false
        while (true) {
            when {
                builder.tokenType === XPathTokenType.WHITE_SPACE -> {
                    skipped = true
                    builder.advanceLexer()
                }
                parseComment(builder) -> skipped = true
                else -> return skipped
            }
        }
    }

    // endregion
    // region Lexical Structure :: Terminal Symbols :: EQName

    open val URI_QUALIFIED_NAME: IElementType = XPathElementType.URI_QUALIFIED_NAME
    open val BRACED_URI_LITERAL: IElementType = XPathElementType.BRACED_URI_LITERAL

    fun parseEQNameOrWildcard(builder: PsiBuilder, type: IElementType, endQNameOnSpace: Boolean): Boolean {
        val marker = builder.mark()
        if (
            parseQNameOrWildcard(builder, type, endQNameOnSpace) ||
            parseURIQualifiedNameOrWildcard(builder, type)
        ) {
            if (type === QNAME || type === NCNAME || type === XPathElementType.WILDCARD) {
                marker.drop()
            } else {
                marker.done(type)
            }
            return true
        }
        marker.drop()
        return false
    }

    private fun parseURIQualifiedNameOrWildcard(builder: PsiBuilder, type: IElementType): Boolean {
        val marker = builder.mark()
        if (parseBracedURILiteral(builder)) {
            val localName = parseQNameNCName(builder, QNamePart.URIQualifiedLiteralLocalName, type, false)
            if (type === XPathElementType.WILDCARD && localName === XPathTokenType.STAR) {
                marker.done(XPathElementType.WILDCARD)
            } else {
                marker.done(URI_QUALIFIED_NAME)
            }
            return true
        }
        marker.drop()
        return false
    }

    open fun parseBracedURILiteral(builder: PsiBuilder): Boolean {
        val marker = builder.matchTokenTypeWithMarker(XPathTokenType.BRACED_URI_LITERAL_START)
        while (marker != null) {
            when {
                builder.matchTokenType(XPathTokenType.STRING_LITERAL_CONTENTS) -> {
                    //
                }
                builder.matchTokenType(XPathTokenType.BRACED_URI_LITERAL_END) -> {
                    marker.done(BRACED_URI_LITERAL)
                    return true
                }
                else -> {
                    marker.done(BRACED_URI_LITERAL)
                    builder.error(XPathBundle.message("parser.error.incomplete-braced-uri-literal"))
                    return true
                }
            }
        }
        return false
    }

    // endregion
    // region Lexical Structure :: Terminal Symbols :: QName

    open val NCNAME: IElementType = XPathElementType.NCNAME
    open val QNAME: IElementType = XPathElementType.QNAME

    open fun parseQNameOrWildcard(
        builder: PsiBuilder,
        type: IElementType,
        endQNameOnSpace: Boolean = false
    ): Boolean {
        val marker = builder.mark()
        val prefix = parseQNameNCName(builder, QNamePart.Prefix, type, false)
        if (prefix != null) {
            if (parseQNameWhitespace(builder, QNamePart.Prefix, endQNameOnSpace, prefix === XPathTokenType.STAR)) {
                if (type === XPathElementType.WILDCARD && prefix === XPathTokenType.STAR) {
                    marker.done(XPathElementType.WILDCARD)
                } else {
                    marker.done(NCNAME)
                }
                return true
            }

            val nameMarker = builder.mark()
            if (parseQNameSeparator(builder, type)) {
                if (
                    parseQNameWhitespace(builder, QNamePart.LocalName, endQNameOnSpace, prefix === XPathTokenType.STAR)
                ) {
                    nameMarker.rollbackTo()
                    if (type === XPathElementType.WILDCARD && prefix === XPathTokenType.STAR) {
                        marker.done(XPathElementType.WILDCARD)
                    } else {
                        marker.done(NCNAME)
                    }
                    return true
                }
                nameMarker.drop()

                val localName = parseQNameNCName(builder, QNamePart.LocalName, type, prefix == XPathTokenType.STAR)
                if (
                    type === XPathElementType.WILDCARD &&
                    (prefix === XPathTokenType.STAR || localName === XPathTokenType.STAR)
                ) {
                    marker.done(XPathElementType.WILDCARD)
                } else {
                    marker.done(QNAME)
                }
            } else if (type === XPathElementType.WILDCARD && prefix == XPathTokenType.STAR) {
                nameMarker.drop()
                marker.done(XPathElementType.WILDCARD)
            } else {
                nameMarker.drop()
                marker.done(NCNAME)
            }
            return true
        }

        if (parseQNameSeparator(builder, null)) { // Missing prefix
            builder.advanceLexer()
            parseWhiteSpaceAndCommentTokens(builder)
            if (builder.tokenType is INCNameType || builder.tokenType == XPathTokenType.STAR) {
                builder.advanceLexer()
            }
            if (type === NCNAME) {
                marker.error(XPathBundle.message("parser.error.expected-ncname-not-qname"))
            } else {
                marker.error(XPathBundle.message("parser.error.qname.missing-prefix"))
            }
            return true
        }

        marker.drop()
        return false
    }

    enum class QNamePart {
        Prefix,
        LocalName,
        URIQualifiedLiteralLocalName
    }

    private fun parseQNameNCName(
        builder: PsiBuilder,
        partType: QNamePart,
        elementType: IElementType,
        isWildcard: Boolean
    ): IElementType? {
        val tokenType = builder.tokenType
        if (tokenType is INCNameType) {
            builder.advanceLexer()
            return tokenType
        } else if (tokenType == XPathTokenType.STAR) {
            if (elementType === XPathElementType.WILDCARD) {
                if (isWildcard) {
                    builder.error(XPathBundle.message("parser.error.wildcard.both-prefix-and-local-wildcard"))
                }
            } else if (partType === QNamePart.Prefix) {
                builder.error(XPathBundle.message("parser.error.unexpected-wildcard"))
            } else if (partType === QNamePart.URIQualifiedLiteralLocalName) {
                builder.error(XPathBundle.message("parser.error.eqname.wildcard-local-name"))
            } else {
                builder.error(XPathBundle.message("parser.error.qname.wildcard-local-name"))
            }
            builder.advanceLexer()
            return tokenType
        } else if (tokenType == XPathTokenType.INTEGER_LITERAL && partType == QNamePart.LocalName) {
            // The user has started the local name with a number, so treat it as part of the QName.
            val errorMarker = builder.mark()
            builder.advanceLexer()
            errorMarker.error(XPathBundle.message("parser.error.qname.missing-local-name"))
        } else if (partType == QNamePart.LocalName) {
            // Don't consume the next token with an error, as it may be a valid part of the next construct
            // (e.g. the start of a string literal, or the '>' of a direct element constructor).
            builder.error(XPathBundle.message("parser.error.qname.missing-local-name"))
        } else if (partType === QNamePart.URIQualifiedLiteralLocalName) {
            // Don't consume the next token with an error, as it may be a valid part of the next construct
            // (e.g. the start of a string literal, or the '>' of a direct element constructor).
            builder.error(XPathBundle.message("parser.error.eqname.missing-local-name"))
        }
        return null
    }

    private fun parseQNameWhitespace(
        builder: PsiBuilder,
        type: QNamePart,
        endQNameOnWhitespace: Boolean,
        isWildcard: Boolean
    ): Boolean {
        val marker = builder.mark()
        if (parseWhiteSpaceAndCommentTokens(builder)) {
            if (endQNameOnWhitespace) {
                marker.drop()
                return true
            } else if (type == QNamePart.Prefix && parseQNameSeparator(builder, null)) {
                if (isWildcard)
                    marker.error(XPathBundle.message("parser.error.wildcard.whitespace-before-local-part"))
                else
                    marker.error(XPathBundle.message("parser.error.qname.whitespace-before-local-part"))
                return false
            } else if (type == QNamePart.LocalName) {
                if (isWildcard)
                    marker.error(XPathBundle.message("parser.error.wildcard.whitespace-after-local-part"))
                else
                    marker.error(XPathBundle.message("parser.error.qname.whitespace-after-local-part"))
                return false
            }
        }
        marker.drop()
        return false
    }

    open fun parseQNameSeparator(builder: PsiBuilder, type: IElementType?): Boolean {
        if (builder.tokenType === XPathTokenType.QNAME_SEPARATOR) {
            if (type != null) {
                builder.advanceLexer()
            }
            return true
        }
        return false
    }

    // endregion
}
