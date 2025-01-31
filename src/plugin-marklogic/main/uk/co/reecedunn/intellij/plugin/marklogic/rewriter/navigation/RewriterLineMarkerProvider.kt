/*
 * Copyright (C) 2020-2022 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.marklogic.rewriter.navigation

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.compat.codeInsight.navigation.setCellRendererEx
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafElement
import uk.co.reecedunn.intellij.plugin.marklogic.resources.MarkLogicIcons
import uk.co.reecedunn.intellij.plugin.processor.resources.PluginApiBundle
import uk.co.reecedunn.intellij.plugin.marklogic.rewriter.endpoints.Rewriter
import uk.co.reecedunn.intellij.plugin.marklogic.rewriter.endpoints.RewriterEndpoint
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryQueryBody

class RewriterLineMarkerProvider : LineMarkerProvider {
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element !is LeafElement) return null

        val body = getQueryBody(element) ?: return null
        val endpoints = getTargetEndpoints(body)
        if (endpoints.isEmpty()) return null

        return NavigationGutterIconBuilder.create(MarkLogicIcons.Markers.Endpoint)
            .setTargets(endpoints.map { it.endpoint })
            .setTooltipText(PluginApiBundle.message("line-marker.rewriter-endpoint.tooltip-text"))
            .setCellRendererEx { RewriterListCellRenderer(endpoints) }
            .createLineMarkerInfo(element)
    }

    private fun getQueryBody(element: PsiElement): XQueryQueryBody? = when {
        element is XQueryQueryBody -> element
        element.prevSibling != null -> null
        else -> element.parent?.let { getQueryBody(it) }
    }

    private fun getTargetEndpoints(element: XQueryQueryBody): List<RewriterEndpoint> {
        return Rewriter.getInstance().getEndpointGroups(element.project).flatMap { group ->
            group.endpoints.filter { it.endpointTarget?.resolve() === element }
        }
    }
}
