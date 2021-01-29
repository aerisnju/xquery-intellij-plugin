/*
 * Copyright (C) 2018-2021 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.processor.query

import com.intellij.xdebugger.frame.XStackFrame
import uk.co.reecedunn.intellij.plugin.processor.intellij.xdebugger.QuerySourcePosition
import java.io.PrintStream
import java.io.PrintWriter

data class QueryError(
    val standardCode: String,
    val vendorCode: String?,
    val description: String?,
    val value: List<String>,
    val frames: List<XStackFrame>
) : RuntimeException() {

    override val message: String
        get() = description?.let { "[$standardCode] $it" } ?: standardCode

    override fun toString(): String = message

    @Suppress("DuplicatedCode")
    override fun printStackTrace(s: PrintStream) {
        s.println(message)
        frames.forEach { frame ->
            when (val source = frame.sourcePosition) {
                null -> {
                }
                is QuerySourcePosition -> s.println("\tat ${source.file.path}:${source.line + 1}:${source.column + 1}")
                else -> s.println("\tat ${source.file.path}:${source.line + 1}")
            }
        }
    }

    @Suppress("DuplicatedCode")
    override fun printStackTrace(s: PrintWriter) {
        s.println(message)
        frames.forEach { frame ->
            when (val source = frame.sourcePosition) {
                null -> {
                }
                is QuerySourcePosition -> s.println("\tat ${source.file.path}:${source.line + 1}:${source.column + 1}")
                else -> s.println("\tat ${source.file.path}:${source.line + 1}")
            }
        }
    }
}
