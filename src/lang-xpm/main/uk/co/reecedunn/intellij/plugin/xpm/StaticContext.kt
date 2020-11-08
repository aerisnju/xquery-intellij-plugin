/*
 * Copyright (C) 2020 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xpm

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import uk.co.reecedunn.intellij.plugin.xpm.optree.function.XpmFunctionDeclaration
import uk.co.reecedunn.intellij.plugin.xpm.optree.function.XpmStaticallyKnownFunctionProvider
import uk.co.reecedunn.intellij.plugin.xpm.optree.variable.XpmInScopeVariableProvider
import uk.co.reecedunn.intellij.plugin.xpm.optree.variable.XpmVariableDefinition

fun PsiElement.inScopeVariables(): Sequence<XpmVariableDefinition> {
    return XpmInScopeVariableProvider.EP_NAME.extensionList.asSequence().flatMap {
        it.getInstance().inScopeVariables(this)
    }
}

fun PsiFile.staticallyKnownFunctions(): Sequence<XpmFunctionDeclaration?> {
    return XpmStaticallyKnownFunctionProvider.EP_NAME.extensionList.asSequence().flatMap {
        it.getInstance().staticallyKnownFunctions(this)
    }
}