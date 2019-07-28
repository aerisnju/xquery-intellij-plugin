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
package uk.co.reecedunn.intellij.plugin.intellij.lang

import com.intellij.codeInsight.CodeInsightBundle
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.lang.parameterInfo.*
import com.intellij.psi.NavigatablePsiElement
import uk.co.reecedunn.intellij.plugin.core.sequences.ancestors
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathArgumentList
import uk.co.reecedunn.intellij.plugin.xpath.lexer.XPathTokenType
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathFunctionDeclaration
import uk.co.reecedunn.intellij.plugin.xpath.model.XPathFunctionReference

object XPathParameterInfoHandler : ParameterInfoHandler<XPathArgumentList, XPathFunctionDeclaration> {
    override fun couldShowInLookup(): Boolean = true

    override fun getParametersForLookup(item: LookupElement?, context: ParameterInfoContext?): Array<Any>? {
        return null
    }

    override fun findElementForParameterInfo(context: CreateParameterInfoContext): XPathArgumentList? {
        val e = context.file.findElementAt(context.offset)
        val args = e?.ancestors()?.filterIsInstance<XPathArgumentList>()?.firstOrNull()
        if (args != null) {
            val refs = (args.parent as? XPathFunctionReference)?.functionName?.element?.references ?: return args
            context.itemsToShow = refs.mapNotNull {
                it.resolve()?.ancestors()?.filterIsInstance<XPathFunctionDeclaration>()?.firstOrNull()
            }.toTypedArray()
        }
        return args
    }

    override fun showParameterInfo(element: XPathArgumentList, context: CreateParameterInfoContext) {
        context.showHint(element, element.textOffset, this)
    }

    override fun findElementForUpdatingParameterInfo(context: UpdateParameterInfoContext): XPathArgumentList? {
        val e = context.file.findElementAt(context.offset)
        return e?.ancestors()?.filterIsInstance<XPathArgumentList>()?.firstOrNull()
    }

    override fun updateParameterInfo(parameterOwner: XPathArgumentList, context: UpdateParameterInfoContext) {
        context.setCurrentParameter(
            ParameterInfoUtils.getCurrentParameterIndex(parameterOwner.node, context.offset, XPathTokenType.COMMA)
        )
    }

    override fun updateUI(p: XPathFunctionDeclaration?, context: ParameterInfoUIContext) {
        if (p == null) return

        val params = p.params.map { (it as NavigatablePsiElement).presentation?.presentableText!! }
        if (params.isNotEmpty()) {
            val isVariadic = p.isVariadic
            var start = -1
            var end = -1
            params.withIndex().forEach { (i, param) ->
                if (i <= context.currentParameterIndex) {
                    start = if (i == 0) 0 else end + PARAM_SEPARATOR.length
                    end = start + param.length
                    if (i == params.size - 1 && isVariadic) {
                        end += VARIADIC_MARKER.length
                    }
                }
            }

            context.setupUIComponentPresentation(
                if (isVariadic)
                    "${params.joinToString(PARAM_SEPARATOR)}$VARIADIC_MARKER"
                else
                    params.joinToString(", "),
                start, end, false, false, false, context.defaultParameterColor
            )
        } else {
            context.setupUIComponentPresentation(
                CodeInsightBundle.message("parameter.info.no.parameters"),
                -1, -1, false, false, false, context.defaultParameterColor
            )
        }
    }

    private const val PARAM_SEPARATOR = ", "
    private const val VARIADIC_MARKER = " ..."
}
