/*
 * Copyright (C) 2021 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.core.execution.testframework

import com.intellij.execution.process.ProcessHandler
import com.intellij.openapi.util.Key
import java.lang.ref.WeakReference

open class TestProcessHandlerEvents private constructor(private val processHandler: WeakReference<ProcessHandler>) {
    constructor(processHandler: ProcessHandler) : this(WeakReference(processHandler))

    fun notifyTextAvailable(text: String, outputType: Key<*>) {
        processHandler.get()?.notifyTextAvailable(text, outputType)
    }
}
