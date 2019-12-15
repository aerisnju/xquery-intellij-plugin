/*
 * Copyright (C) 2016, 2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.intellij.lang.findUsages

import com.intellij.lang.HelpID
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import uk.co.reecedunn.intellij.plugin.intellij.lang.cacheBuilder.XPathWordsScanner
import uk.co.reecedunn.intellij.plugin.intellij.resources.XPathBundle
import uk.co.reecedunn.intellij.plugin.xpath.parser.XPathElementType

object XPathFindUsagesProvider : FindUsagesProvider {
    private val TYPE = mapOf(
        XPathElementType.ARROW_FUNCTION_SPECIFIER to XPathBundle.message("find-usages.function"),
        XPathElementType.FUNCTION_CALL to XPathBundle.message("find-usages.function"),
        XPathElementType.NAMED_FUNCTION_REF to XPathBundle.message("find-usages.function")
    )

    override fun getWordsScanner(): WordsScanner? = XPathWordsScanner()

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean = psiElement is PsiNamedElement

    override fun getHelpId(psiElement: PsiElement): String? = HelpID.FIND_OTHER_USAGES

    override fun getType(element: PsiElement): String {
        val parentType = element.parent.node.elementType
        return TYPE.getOrElse(parentType) { XPathBundle.message("find-usages.identifier") }
    }

    override fun getDescriptiveName(element: PsiElement): String {
        val name = (element as PsiNamedElement).name
        return name ?: ""
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String = getDescriptiveName(element)
}
