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
package uk.co.reecedunn.intellij.plugin.xqdoc.documentation

import com.intellij.util.text.nullize
import uk.co.reecedunn.intellij.plugin.xdm.module.path.XdmModuleType

interface XQDocDocumentation {
    val moduleTypes: Array<XdmModuleType>

    fun href(moduleType: XdmModuleType): String?

    fun summary(moduleType: XdmModuleType): String?

    fun notes(moduleType: XdmModuleType): String?

    fun examples(moduleType: XdmModuleType): Sequence<String>
}

interface XQDocFunctionDocumentation : XQDocDocumentation {
    val operatorMapping: String?

    val signatures: String?

    val properties: String?

    val privileges: String?

    fun rules(moduleType: XdmModuleType): String?

    val errorConditions: String?
}

fun XQDocDocumentation.sections(moduleType: XdmModuleType): String {
    val sections = sequenceOf(
        "Summary" to summary(moduleType),
        "Operator Mapping" to (this as? XQDocFunctionDocumentation)?.operatorMapping,
        "Signatures" to (this as? XQDocFunctionDocumentation)?.signatures,
        "Properties" to (this as? XQDocFunctionDocumentation)?.properties,
        "Required Privileges" to (this as? XQDocFunctionDocumentation)?.privileges,
        "Rules" to (this as? XQDocFunctionDocumentation)?.rules(moduleType),
        "Error Conditions" to (this as? XQDocFunctionDocumentation)?.errorConditions,
        "Notes" to notes(moduleType),
        "Examples" to examples(moduleType).joinToString("\n").nullize()
    ).filter { it.second != null }
    return "<dl>${sections.joinToString("") { "<dt>${it.first}</dt><dd>${it.second}</dd>" }}</dl>"
}