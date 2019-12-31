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
package uk.co.reecedunn.intellij.plugin.xquery.psi.impl.xquery

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import uk.co.reecedunn.intellij.plugin.core.data.CacheableProperty
import uk.co.reecedunn.intellij.plugin.core.psi.contains
import uk.co.reecedunn.intellij.plugin.core.sequences.children
import uk.co.reecedunn.intellij.plugin.xdm.types.XdmUriContext
import uk.co.reecedunn.intellij.plugin.xdm.model.XsAnyAtomicType
import uk.co.reecedunn.intellij.plugin.xdm.model.XsAnyUri
import uk.co.reecedunn.intellij.plugin.xdm.model.XsString
import uk.co.reecedunn.intellij.plugin.xdm.module.path.XdmModuleType
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathEscapeCharacter
import uk.co.reecedunn.intellij.plugin.xpath.model.*
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryCharRef
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryDirAttributeValue
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryPredefinedEntityRef
import uk.co.reecedunn.intellij.plugin.xquery.lexer.XQueryTokenType
import uk.co.reecedunn.intellij.plugin.xquery.parser.XQueryElementType

class XQueryDirAttributeValuePsiImpl(node: ASTNode) :
    ASTWrapperPsiElement(node),
    XQueryDirAttributeValue {

    override fun subtreeChanged() {
        super.subtreeChanged()
        cachedContent.invalidate()
    }

    override val value
        get(): XsAnyAtomicType? {
            return cachedContent.get()?.let {
                if ((parent as XPathDefaultNamespaceDeclaration).accepts(XPathNamespaceType.Undefined)) {
                    XsString(it, this)
                } else {
                    XsAnyUri(it, XdmUriContext.NamespaceDeclaration, XdmModuleType.MODULE_OR_SCHEMA, this)
                }
            }
        }

    private val cachedContent = CacheableProperty {
        if (contains(XQueryElementType.ENCLOSED_EXPR))
            null // Cannot evaluate enclosed content expressions statically.
        else
            children().map { child ->
                when (child.node.elementType) {
                    XQueryTokenType.XML_ATTRIBUTE_VALUE_START, XQueryTokenType.XML_ATTRIBUTE_VALUE_END ->
                        null
                    XQueryTokenType.XML_PREDEFINED_ENTITY_REFERENCE ->
                        (child as XQueryPredefinedEntityRef).entityRef.value
                    XQueryTokenType.XML_CHARACTER_REFERENCE ->
                        (child as XQueryCharRef).codepoint.toString()
                    XQueryTokenType.XML_ESCAPED_CHARACTER ->
                        (child as XPathEscapeCharacter).unescapedValue
                    else ->
                        child.text
                }
            }.filterNotNull().joinToString(separator = "")
    }
}