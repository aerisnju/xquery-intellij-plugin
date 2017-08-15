/*
 * Copyright (C) 2016-2017 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.tests.psi

import com.intellij.psi.tree.IElementType
import uk.co.reecedunn.intellij.plugin.xquery.ast.marklogic.*
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.*
import uk.co.reecedunn.intellij.plugin.xquery.lang.Implementations
import uk.co.reecedunn.intellij.plugin.xquery.lexer.XQueryTokenType
import uk.co.reecedunn.intellij.plugin.xquery.parser.XQueryElementType
import uk.co.reecedunn.intellij.plugin.xquery.psi.XQueryConformanceCheck
import uk.co.reecedunn.intellij.plugin.xquery.tests.parser.ParserTestCase

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import uk.co.reecedunn.intellij.plugin.core.extensions.children
import uk.co.reecedunn.intellij.plugin.core.extensions.descendants

class MarkLogicPsiTest : ParserTestCase() {
    // region XQueryConformanceCheck
    // region AnyKindTest

    fun testAnyKindTest_KeyName() {
        val file = parseResource("tests/parser/marklogic-8.0/AnyKindTest_KeyName.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val anyKindTestPsi = sequenceTypePsi.descendants().filterIsInstance<XQueryAnyKindTest>().first()
        val versioned = anyKindTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryElementType.STRING_LITERAL))
    }

    fun testAnyKindTest_Wildcard() {
        val file = parseResource("tests/parser/marklogic-8.0/AnyKindTest_Wildcard.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val anyKindTestPsi = sequenceTypePsi.descendants().filterIsInstance<XQueryAnyKindTest>().first()
        val versioned = anyKindTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.STAR))
    }

    // endregion
    // region ArrayConstructor

    fun testArrayConstructor() {
        val file = parseResource("tests/parser/marklogic-8.0/ArrayConstructor.xq")!!

        val arrayConstructorPsi = file.descendants().filterIsInstance<XQueryArrayConstructor>().first()
        val versioned = arrayConstructorPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ARRAY_NODE))
    }

    // endregion
    // region ArrayTest

    fun testArrayTest() {
        val file = parseResource("tests/parser/marklogic-8.0/ArrayTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val arrayTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicArrayTest>().first()

        val versioned = arrayTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ARRAY_NODE))
    }

    // endregion
    // region AttributeDeclTest

    fun testAttributeDeclTest() {
        val file = parseResource("tests/parser/marklogic-7.0/AttributeDeclTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val attributeDeclTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicAttributeDeclTest>().first()

        val versioned = attributeDeclTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ATTRIBUTE_DECL))
    }

    // endregion
    // region BinaryConstructor

    fun testBinaryConstructor() {
        val file = parseResource("tests/parser/marklogic-6.0/BinaryConstructor.xq")!!

        val binaryKindTestPsi = file.descendants().filterIsInstance<MarkLogicBinaryConstructor>().first()
        val versioned = binaryKindTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BINARY))
    }

    // endregion
    // region BinaryTest

    fun testBinaryTest() {
        val file = parseResource("tests/parser/marklogic-6.0/BinaryTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val binaryKindTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicBinaryTest>().first()

        val versioned = binaryKindTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BINARY))
    }

    // endregion
    // region BooleanConstructor

    fun testBooleanConstructor() {
        val file = parseResource("tests/parser/marklogic-8.0/BooleanConstructor.xq")!!

        val booleanConstructorPsi = file.descendants().filterIsInstance<MarkLogicBooleanConstructor>().first()
        val versioned = booleanConstructorPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BOOLEAN_NODE))
    }

    // endregion
    // region BooleanTest

    fun testBooleanTest() {
        val file = parseResource("tests/parser/marklogic-8.0/BooleanTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val booleanTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicBooleanTest>().first()

        val versioned = booleanTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BOOLEAN_NODE))
    }

    // endregion
    // region CatchClause

    fun testCatchClause() {
        val file = parseResource("tests/parser/marklogic-6.0/CatchClause.xq")!!

        val tryCatchExprPsi = file.descendants().filterIsInstance<XQueryTryCatchExpr>().first()
        val catchClausePsi = tryCatchExprPsi.children().filterIsInstance<XQueryCatchClause>().first()
        val versioned = catchClausePsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_CATCH))
    }

    // endregion
    // region CompatibilityAnnotation

    fun testCompatibilityAnnotation_FunctionDecl() {
        val file = parseResource("tests/parser/marklogic-6.0/CompatibilityAnnotation_FunctionDecl.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val compatibilityAnnotationPsi = annotatedDeclPsi.children().filterIsInstance<MarkLogicCompatibilityAnnotation>().first()
        val versioned = compatibilityAnnotationPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_PRIVATE))
    }

    fun testCompatibilityAnnotation_VarDecl() {
        val file = parseResource("tests/parser/marklogic-6.0/CompatibilityAnnotation_VarDecl.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val compatibilityAnnotationPsi = annotatedDeclPsi.children().filterIsInstance<MarkLogicCompatibilityAnnotation>().first()
        val versioned = compatibilityAnnotationPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_PRIVATE))
    }

    // endregion
    // region ComplexTypeTest

    fun testComplexTypeTest() {
        val file = parseResource("tests/parser/marklogic-7.0/ComplexTypeTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val complexTypeTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicComplexTypeTest>().first()

        val versioned = complexTypeTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_COMPLEX_TYPE))
    }

    // endregion
    // region ElementDeclTest

    fun testElementDeclTest() {
        val file = parseResource("tests/parser/marklogic-7.0/ElementDeclTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val elementDeclTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicElementDeclTest>().first()

        val versioned = elementDeclTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ELEMENT_DECL))
    }

    // endregion
    // region EnclosedExpr (CatchClause)

    fun testEnclosedExpr_CatchClause() {
        val file = parseResource("tests/parser/marklogic-6.0/CatchClause.xq")!!

        val tryCatchExprPsi = file.descendants().filterIsInstance<XQueryTryCatchExpr>().first()
        val catchClausePsi = tryCatchExprPsi.children().filterIsInstance<XQueryCatchClause>().first()
        val enclosedExprPsi = catchClausePsi.children().filterIsInstance<XQueryEnclosedExpr>().first()
        val versioned = enclosedExprPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.1")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Empty enclosed expressions requires XQuery 3.1 or later."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryElementType.EXPR))
    }

    fun testEnclosedExpr_CatchClause_NoExpr() {
        val file = parseResource("tests/parser/marklogic-6.0/CatchClause_EmptyExpr.xq")!!

        val tryCatchExprPsi = file.descendants().filterIsInstance<XQueryTryCatchExpr>().first()
        val catchClausePsi = tryCatchExprPsi.children().filterIsInstance<XQueryCatchClause>().first()
        val enclosedExprPsi = catchClausePsi.children().filterIsInstance<XQueryEnclosedExpr>().first()
        val versioned = enclosedExprPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.1")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Empty enclosed expressions requires XQuery 3.1 or later."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.BLOCK_OPEN))
    }

    // endregion
    // region ForwardAxis

    fun testForwardAxis_Namespace() {
        val file = parseResource("tests/parser/marklogic-6.0/ForwardAxis_Namespace.xq")!!

        val forwardAxisPsi = file.descendants().filterIsInstance<XQueryForwardAxis>().first()
        val versioned = forwardAxisPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NAMESPACE))
    }

    fun testForwardAxis_Property() {
        val file = parseResource("tests/parser/marklogic-6.0/ForwardAxis_Property.xq")!!

        val forwardAxisPsi = file.descendants().filterIsInstance<XQueryForwardAxis>().first()
        val versioned = forwardAxisPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_PROPERTY))
    }

    // endregion
    // region FunctionCall

    fun testFunctionCall_ArrayNode() {
        val file = parseResource("tests/parser/marklogic-8.0/NodeTest_ArrayTest_FunctionCallLike.xq")!!

        val functionCallPsi = file.descendants().filterIsInstance<XQueryFunctionCall>().first()
        val versioned = functionCallPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This function name conflicts with MarkLogic JSON KindTest keywords."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ARRAY_NODE))
    }

    fun testFunctionCall_BooleanNode() {
        val file = parseResource("tests/parser/marklogic-8.0/NodeTest_BooleanTest_FunctionCallLike.xq")!!

        val functionCallPsi = file.descendants().filterIsInstance<XQueryFunctionCall>().first()
        val versioned = functionCallPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This function name conflicts with MarkLogic JSON KindTest keywords."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat<IElementType>(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BOOLEAN_NODE))
    }

    fun testFunctionCall_NullNode() {
        val file = parseResource("tests/parser/marklogic-8.0/NodeTest_NullTest_FunctionCallLike.xq")!!

        val functionCallPsi = file.descendants().filterIsInstance<XQueryFunctionCall>().first()
        val versioned = functionCallPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This function name conflicts with MarkLogic JSON KindTest keywords."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NULL_NODE))
    }

    fun testFunctionCall_NumberNode() {
        val file = parseResource("tests/parser/marklogic-8.0/NodeTest_NumberTest_FunctionCallLike.xq")!!

        val functionCallPsi = file.descendants().filterIsInstance<XQueryFunctionCall>().first()
        val versioned = functionCallPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This function name conflicts with MarkLogic JSON KindTest keywords."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NUMBER_NODE))
    }

    fun testFunctionCall_ObjectNode() {
        val file = parseResource("tests/parser/marklogic-8.0/NodeTest_MapTest_FunctionCallLike.xq")!!

        val functionCallPsi = file.descendants().filterIsInstance<XQueryFunctionCall>().first()
        val versioned = functionCallPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This function name conflicts with MarkLogic JSON KindTest keywords."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_OBJECT_NODE))
    }

    // endregion
    // region FunctionDecl

    fun testFunctionDecl_ReservedKeyword_ArrayNode() {
        val file = parseResource("tests/psi/marklogic-8.0/FunctionDecl_ReservedKeyword_ArrayNode.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val functionDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryFunctionDecl>().first()
        val versioned = functionDeclPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Reserved keyword used as a function name."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_ARRAY_NODE))
    }

    fun testFunctionDecl_ReservedKeyword_BooleanNode() {
        val file = parseResource("tests/psi/marklogic-8.0/FunctionDecl_ReservedKeyword_BooleanNode.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val functionDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryFunctionDecl>().first()
        val versioned = functionDeclPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Reserved keyword used as a function name."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_BOOLEAN_NODE))
    }

    fun testFunctionDecl_ReservedKeyword_NullNode() {
        val file = parseResource("tests/psi/marklogic-8.0/FunctionDecl_ReservedKeyword_NullNode.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val functionDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryFunctionDecl>().first()
        val versioned = functionDeclPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Reserved keyword used as a function name."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NULL_NODE))
    }

    fun testFunctionDecl_ReservedKeyword_NumberNode() {
        val file = parseResource("tests/psi/marklogic-8.0/FunctionDecl_ReservedKeyword_NumberNode.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val functionDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryFunctionDecl>().first()
        val versioned = functionDeclPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Reserved keyword used as a function name."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NUMBER_NODE))
    }

    fun testFunctionDecl_ReservedKeyword_ObjectNode() {
        val file = parseResource("tests/psi/marklogic-8.0/FunctionDecl_ReservedKeyword_ObjectNode.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val functionDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryFunctionDecl>().first()
        val versioned = functionDeclPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(false))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Reserved keyword used as a function name."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_OBJECT_NODE))
    }

    // endregion
    // region MapConstructor

    fun testMapConstructor() {
        val file = parseResource("tests/parser/marklogic-8.0/MapConstructor.xq")!!

        val objectConstructorPsi = file.descendants().filterIsInstance<XQueryMapConstructor>().first()
        val versioned = objectConstructorPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.1")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.4/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.5/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.6/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.7/3.0")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_OBJECT_NODE))
    }

    // endregion
    // region MapConstructorEntry

    fun testMapConstructorEntry() {
        val file = parseResource("tests/parser/marklogic-8.0/MapConstructorEntry.xq")!!

        val mapConstructorPsi = file.descendants().filterIsInstance<XQueryMapConstructor>().first()
        val mapConstructorEntryPsi = mapConstructorPsi.children().filterIsInstance<XQueryMapConstructorEntry>().first()
        val versioned = mapConstructorEntryPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/3.1")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.4/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.5/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.6/3.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("saxon/EE/v9.7/3.0")), `is`(true))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: Use ':=' for Saxon 9.4 to 9.6, and ':' for XQuery 3.1 and MarkLogic."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.QNAME_SEPARATOR))
    }

    // endregion
    // region MapTest

    fun testMapTest() {
        val file = parseResource("tests/parser/marklogic-8.0/MapTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val objectTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicMapTest>().first()

        val versioned = objectTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_OBJECT_NODE))
    }

    // endregion
    // region NullConstructor

    fun testNullConstructor() {
        val file = parseResource("tests/parser/marklogic-8.0/NullConstructor.xq")!!

        val nullKindTestPsi = file.descendants().filterIsInstance<MarkLogicNullConstructor>().first()
        val versioned = nullKindTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NULL_NODE))
    }

    // endregion
    // region NullTest

    fun testNullTest() {
        val file = parseResource("tests/parser/marklogic-8.0/NullTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val nullTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicNullTest>().first()

        val versioned = nullTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NULL_NODE))
    }

    // endregion
    // region NumberConstructor

    fun testNumberConstructor() {
        val file = parseResource("tests/parser/marklogic-8.0/NumberConstructor.xq")!!

        val numberConstructorPsi = file.descendants().filterIsInstance<MarkLogicNumberConstructor>().first()
        val versioned = numberConstructorPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NUMBER_NODE))
    }

    // endregion
    // region NumberTest

    fun testNumberTest() {
        val file = parseResource("tests/parser/marklogic-8.0/NumberTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val numberTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicNumberTest>().first()

        val versioned = numberTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_NUMBER_NODE))
    }

    // endregion
    // region SchemaComponentTest

    fun testSchemaComponentTest() {
        val file = parseResource("tests/parser/marklogic-7.0/SchemaComponentTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val schemaComponentTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSchemaComponentTest>().first()

        val versioned = schemaComponentTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SCHEMA_COMPONENT))
    }

    // endregion
    // region SchemaFacetTest

    fun testSchemaFacetTest() {
        val file = parseResource("tests/parser/marklogic-8.0/SchemaFacetTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val schemaFacetTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSchemaFacetTest>().first()

        val versioned = schemaFacetTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SCHEMA_FACET))
    }

    // endregion
    // region SchemaParticleTest

    fun testSchemaParticleTest() {
        val file = parseResource("tests/parser/marklogic-7.0/SchemaParticleTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val schemaParticleTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSchemaParticleTest>().first()

        val versioned = schemaParticleTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SCHEMA_PARTICLE))
    }

    // endregion
    // region SchemaRootTest

    fun testSchemaRootTest() {
        val file = parseResource("tests/parser/marklogic-7.0/SchemaRootTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val schemaRootTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSchemaRootTest>().first()

        val versioned = schemaRootTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SCHEMA_ROOT))
    }

    // endregion
    // region SchemaTypeTest

    fun testSchemaTypeTest() {
        val file = parseResource("tests/parser/marklogic-7.0/SchemaTypeTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val schemaTypeTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSchemaTypeTest>().first()

        val versioned = schemaTypeTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SCHEMA_TYPE))
    }

    // endregion
    // region SimpleTypeTest

    fun testSimpleTypeTest() {
        val file = parseResource("tests/parser/marklogic-7.0/SimpleTypeTest.xq")!!

        val annotationDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotationDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val simpleTypeTestPsi = sequenceTypePsi.descendants().filterIsInstance<MarkLogicSimpleTypeTest>().first()

        val versioned = simpleTypeTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 7.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_SIMPLE_TYPE))
    }

    // endregion
    // region StylesheetImport

    fun testStylesheetImport() {
        val file = parseResource("tests/parser/marklogic-6.0/StylesheetImport.xq")!!

        val stylesheetImportPsi = file.descendants().filterIsInstance<MarkLogicStylesheetImport>().first()
        val versioned = stylesheetImportPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_IMPORT))
    }

    // endregion
    // region TextTest

    fun testTextTest_KeyName() {
        val file = parseResource("tests/parser/marklogic-8.0/TextTest_KeyName.xq")!!

        val annotatedDeclPsi = file.descendants().filterIsInstance<XQueryAnnotatedDecl>().first()
        val varDeclPsi = annotatedDeclPsi.children().filterIsInstance<XQueryVarDecl>().first()
        val typeDeclarationPsi = varDeclPsi.children().filterIsInstance<XQueryTypeDeclaration>().first()
        val sequenceTypePsi = typeDeclarationPsi.children().filterIsInstance<XQuerySequenceType>().first()
        val textTestPsi = sequenceTypePsi.descendants().filterIsInstance<XQueryTextTest>().first()
        val versioned = textTestPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))

        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 8.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryElementType.STRING_LITERAL))
    }

    // endregion
    // region Transactions + TransactionSeparator

    fun testTransactions() {
        val file = parseResource("tests/parser/marklogic-6.0/Transactions_WithVersionDecl.xq")!!

        val transactionSeparatorPsi = file.children().filterIsInstance<MarkLogicTransactionSeparator>().first()
        val versioned = transactionSeparatorPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.SEPARATOR))
    }

    // endregion
    // region ValidateExpr

    fun testValidateExpr_ValidateAs() {
        val file = parseResource("tests/parser/marklogic-6.0/ValidateExpr_ValidateAs.xq")!!

        val validateExprPsi = file.descendants().filterIsInstance<XQueryValidateExpr>().first()
        val versioned = validateExprPsi as XQueryConformanceCheck

        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("w3c/1.0-update")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v6/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v7/1.0-ml")), `is`(true))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0")), `is`(false))
        assertThat(versioned.conformsTo(Implementations.getItemById("marklogic/v8/1.0-ml")), `is`(true))

        assertThat(versioned.conformanceErrorMessage,
                `is`("XPST0003: This expression requires MarkLogic 6.0 or later with XQuery version '1.0-ml'."))

        assertThat(versioned.conformanceElement, `is`(notNullValue()))
        assertThat(versioned.conformanceElement.node.elementType,
                `is`<IElementType>(XQueryTokenType.K_AS))
    }

    // endregion
    // endregion
}
