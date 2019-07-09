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
package uk.co.reecedunn.intellij.plugin.xquery.model

import com.intellij.psi.PsiElement
import uk.co.reecedunn.intellij.plugin.core.sequences.ancestors
import uk.co.reecedunn.intellij.plugin.core.sequences.children
import uk.co.reecedunn.intellij.plugin.xpath.ast.xpath.XPathEQName
import uk.co.reecedunn.intellij.plugin.xpath.model.XsQNameValue
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryAnnotatedDecl
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryLibraryModule
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryMainModule
import uk.co.reecedunn.intellij.plugin.xquery.ast.xquery.XQueryProlog

fun <Decl> XQueryProlog.annotatedDeclarations(klass: Class<Decl>): Sequence<Decl?> {
    return children().reversed().filterIsInstance<XQueryAnnotatedDecl>().map { annotation ->
        annotation.children().filterIsInstance(klass).firstOrNull()
    }
}

inline fun <reified Decl> XQueryProlog.annotatedDeclarations(): Sequence<Decl?> =
    annotatedDeclarations(Decl::class.java)

fun PsiElement.fileProlog(): XQueryProlog? {
    val module = ancestors().filter { it is XQueryMainModule || it is XQueryLibraryModule }.first()
    return (module as XQueryPrologResolver).prolog.firstOrNull()
}

private class PrologVisitor(
    private val prologs: Iterator<Pair<XsQNameValue?, XQueryProlog>>
) : Iterator<Pair<XsQNameValue?, XQueryProlog>> {
    var item: Pair<XsQNameValue?, XQueryProlog>? = null
    val visited: MutableSet<XQueryProlog> = mutableSetOf()

    override fun hasNext(): Boolean {
        while (prologs.hasNext()) {
            item = prologs.next()
            if (!visited.contains(item!!.second)) {
                visited.add(item!!.second)
                return true
            }
        }
        return false
    }

    override fun next(): Pair<XsQNameValue?, XQueryProlog> = item!!
}

fun XQueryProlog.importedPrologs(): Sequence<XQueryProlog> {
    return sequenceOf(
        sequenceOf(this),
        staticallyKnownNamespaces().flatMap { ns ->
            (ns.namespaceUri?.element?.parent as? XQueryPrologResolver)?.prolog ?: emptySequence()
        }
    ).flatten()
}

fun XPathEQName.importedPrologsForQName(): Sequence<Pair<XsQNameValue?, XQueryProlog>> {
    val prologs = sequenceOf(
        (this as XsQNameValue).expand().flatMap { name ->
            (name.namespace?.element?.parent as? XQueryPrologResolver)?.prolog?.map { prolog ->
                name to prolog
            } ?: emptySequence()
        },
        fileProlog()?.let { (this as XsQNameValue).expand().map { name -> name to it } } ?: emptySequence()
    ).flatten()
    return PrologVisitor(prologs.iterator()).asSequence()
}
