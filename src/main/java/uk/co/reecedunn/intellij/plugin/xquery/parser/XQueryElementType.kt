/*
 * Copyright (C) 2016-2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.parser

import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import uk.co.reecedunn.intellij.plugin.core.parser.ICompositeElementType
import uk.co.reecedunn.intellij.plugin.intellij.lang.XQuery
import uk.co.reecedunn.intellij.plugin.xquery.psi.impl.plugin.*
import uk.co.reecedunn.intellij.plugin.xquery.psi.impl.scripting.*
import uk.co.reecedunn.intellij.plugin.xquery.psi.impl.xquery.*

object XQueryElementType2 {
    // region XQuery 1.0

    val NCNAME: IElementType = ICompositeElementType(
        "XQUERY_NCNAME",
        XQueryNCNamePsiImpl::class.java,
        XQuery
    )
    val QNAME: IElementType = ICompositeElementType(
        "XQUERY_QNAME",
        XQueryQNamePsiImpl::class.java,
        XQuery
    )
    val URI_LITERAL: IElementType = ICompositeElementType(
        "XQUERY_URI_LITERAL",
        XQueryUriLiteralPsiImpl::class.java,
        XQuery
    )

    val QUERY_BODY: IElementType = ICompositeElementType(
        "XQUERY_QUERY_BODY",
        XQueryQueryBodyPsiImpl::class.java,
        XQuery
    )
    val EXPR: IElementType = ICompositeElementType(
        "XQUERY_EXPR",
        XQueryExprPsiImpl::class.java,
        XQuery
    )
    val ENCLOSED_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ENCLOSED_EXPR",
        XQueryEnclosedExprPsiImpl::class.java,
        XQuery
    )

    val NAMESPACE_DECL: IElementType = ICompositeElementType(
        "XQUERY_NAMESPACE_DECL",
        XQueryNamespaceDeclPsiImpl::class.java,
        XQuery
    )
    val SCHEMA_IMPORT: IElementType = ICompositeElementType(
        "XQUERY_SCHEMA_IMPORT",
        XQuerySchemaImportPsiImpl::class.java,
        XQuery
    )
    val MODULE_IMPORT: IElementType = ICompositeElementType(
        "XQUERY_MODULE_IMPORT",
        XQueryModuleImportPsiImpl::class.java,
        XQuery
    )

    val DEFAULT_NAMESPACE_DECL: IElementType = ICompositeElementType(
        "XQUERY_DEFAULT_NAMESPACE_DECL",
        XQueryDefaultNamespaceDeclPsiImpl::class.java,
        XQuery
    )
    val MODULE_DECL: IElementType = ICompositeElementType(
        "XQUERY_MODULE_DECL",
        XQueryModuleDeclPsiImpl::class.java,
        XQuery
    )
    val VERSION_DECL: IElementType = ICompositeElementType(
        "XQUERY_VERSION_DECL",
        XQueryVersionDeclPsiImpl::class.java,
        XQuery
    )

    val MODULE = IFileElementType(XQuery)
    val MAIN_MODULE: IElementType = ICompositeElementType(
        "XQUERY_MAIN_MODULE",
        XQueryMainModulePsiImpl::class.java,
        XQuery
    )
    val LIBRARY_MODULE: IElementType = ICompositeElementType(
        "XQUERY_LIBRARY_MODULE",
        XQueryLibraryModulePsiImpl::class.java,
        XQuery
    )

    val DIR_ELEM_CONSTRUCTOR: IElementType = ICompositeElementType(
        "XQUERY_DIR_ELEM_CONSTRUCTOR",
        XQueryDirElemConstructorPsiImpl::class.java,
        XQuery
    )
    val DIR_ATTRIBUTE_VALUE: IElementType = ICompositeElementType(
        "XQUERY_DIR_ATTRIBUTE_VALUE",
        XQueryDirAttributeValuePsiImpl::class.java,
        XQuery
    )

    // endregion
    // region XQuery 3.0

    val URI_QUALIFIED_NAME: IElementType = ICompositeElementType(
        "XQUERY_URI_QUALIFIED_NAME",
        XQueryURIQualifiedNamePsiImpl::class.java,
        XQuery
    )

    val FUNCTION_BODY: IElementType = ICompositeElementType(
        "XQUERY_FUNCTION_BODY",
        XQueryFunctionBodyPsiImpl::class.java,
        XQuery
    )

    val FUNCTION_TEST: IElementType = ICompositeElementType(
        "XQUERY_FUNCTION_TEST",
        XQueryFunctionTestPsiImpl::class.java,
        XQuery
    )

    val INTERMEDIATE_CLAUSE: IElementType = ICompositeElementType(
        "XQUERY_INTERMEDIATE_CLAUSE",
        XQueryIntermediateClausePsiImpl::class.java,
        XQuery
    )

    val PREFIX: IElementType = ICompositeElementType(
        "XQUERY_PREFIX",
        XQueryPrefixPsiImpl::class.java,
        XQuery
    )

    // endregion
    // region XQuery 3.1

    val ENCLOSED_CONTENT_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ENCLOSED_CONTENT_EXPR",
        XQueryEnclosedContentExprPsiImpl::class.java,
        XQuery
    )
    val ENCLOSED_TRY_TARGET_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ENCLOSED_TRY_TARGET_EXPR",
        XQueryEnclosedTryTargetExprPsiImpl::class.java,
        XQuery
    )
    val ENCLOSED_URI_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ENCLOSED_URI_EXPR",
        XQueryEnclosedUriExprPsiImpl::class.java,
        XQuery
    )
    val ENCLOSED_PREFIX_EXPR: IElementType = ICompositeElementType(
        "XQUERY_ENCLOSED_PREFIX_EXPR",
        XQueryEnclosedPrefixExprPsiImpl::class.java,
        XQuery
    )

    // endregion
    // region Scripting Extension 1.0

    val BLOCK_BODY: IElementType = ICompositeElementType(
        "XQUERY_BLOCK_BODY",
        ScriptingBlockBodyPsiImpl::class.java,
        XQuery
    )

    val BLOCK_VAR_DECL: IElementType = ICompositeElementType(
        "XQUERY_BLOCK_VAR_DECL",
        ScriptingBlockVarDeclPsiImpl::class.java,
        XQuery
    )

    // endregion
    // region MarkLogic 6.0

    val TRANSACTION_SEPARATOR: IElementType = ICompositeElementType(
        "XQUERY_TRANSACTION_SEPARATOR",
        PluginTransactionSeparatorPsiImpl::class.java,
        XQuery
    )

    // endregion
    // region XQuery IntelliJ Plugin

    val DIR_ATTRIBUTE: IElementType = ICompositeElementType(
        "XQUERY_DIR_ATTRIBUTE",
        PluginDirAttributePsiImpl::class.java,
        XQuery
    )

    val NAMED_KIND_TEST: IElementType = ICompositeElementType(
        "XQUERY_NAMED_KIND_TEST",
        PluginNamedKindTestPsiImpl::class.java,
        XQuery
    )

    val NAMED_TEXT_TEST: IElementType = ICompositeElementType(
        "XQUERY_NAMED_TEXT_TEST",
        PluginNamedTextTestPsiImpl::class.java,
        XQuery
    )

    // endregion
}
