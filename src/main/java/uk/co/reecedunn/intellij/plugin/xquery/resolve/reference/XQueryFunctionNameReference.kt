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
package uk.co.reecedunn.intellij.plugin.xquery.resolve.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReferenceBase
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryArrowFunctionSpecifier
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryEQName
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryFunctionCall
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryNamedFunctionRef

class XQueryFunctionNameReference(element: XQueryEQName, range: TextRange) : PsiReferenceBase<XQueryEQName>(element, range) {
    override fun resolve(): PsiElement? {
        val parent = element.parent
        val arity = when (parent) {
            is XQueryFunctionCall -> parent.arity
            is XQueryNamedFunctionRef -> parent.arity
            is XQueryArrowFunctionSpecifier -> parent.arity
            else -> -1
        }
        return element.resolveFunctionDecls().firstOrNull { f -> f.arity == arity }
    }

    override fun getVariants(): Array<Any> {
        return arrayOf()
    }
}
