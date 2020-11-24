/*
 * Copyright (C) 2019-2020 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.marklogic.tests.log

import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.HyperlinkInfo
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.extensions.PluginId
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.*
import uk.co.reecedunn.intellij.plugin.core.tests.assertion.assertThat
import uk.co.reecedunn.intellij.plugin.core.tests.testFramework.IdeaPlatformTestCase
import uk.co.reecedunn.intellij.plugin.marklogic.log.MarkLogicErrorLogLine
import java.lang.UnsupportedOperationException
import javax.swing.JComponent

@Suppress("XmlPathReference")
@DisplayName("IntelliJ - Base Platform - Run Configuration - Query Log - MarkLogic ErrorLog")
class MarkLogicErrorLogLineTest : IdeaPlatformTestCase(), ConsoleView {
    override val pluginId: PluginId = PluginId.getId("MarkLogicErrorLogLineTest")

    // region ConsoleView

    private var printed: String = ""

    private fun reset() {
        printed = ""
    }

    override fun addMessageFilter(filter: Filter) = throw UnsupportedOperationException()

    override fun allowHeavyFilters() = throw UnsupportedOperationException()

    override fun attachToProcess(processHandler: ProcessHandler) = throw UnsupportedOperationException()

    override fun canPause(): Boolean = throw UnsupportedOperationException()

    override fun clear() = throw UnsupportedOperationException()

    override fun createConsoleActions(): Array<AnAction> = throw UnsupportedOperationException()

    override fun dispose() = throw UnsupportedOperationException()

    override fun getComponent(): JComponent = throw UnsupportedOperationException()

    override fun getContentSize(): Int = throw UnsupportedOperationException()

    override fun getPreferredFocusableComponent(): JComponent = throw UnsupportedOperationException()

    override fun hasDeferredOutput(): Boolean = throw UnsupportedOperationException()

    override fun isOutputPaused(): Boolean = throw UnsupportedOperationException()

    override fun performWhenNoDeferredOutput(runnable: Runnable) = throw UnsupportedOperationException()

    override fun print(text: String, contentType: ConsoleViewContentType) {
        printed += "[$contentType|$text]"
    }

    override fun printHyperlink(hyperlinkText: String, info: HyperlinkInfo?) = throw UnsupportedOperationException()

    override fun scrollTo(offset: Int) = throw UnsupportedOperationException()

    override fun setHelpId(helpId: String) = throw UnsupportedOperationException()

    override fun setOutputPaused(value: Boolean) = throw UnsupportedOperationException()

    // endregion

    @Test
    @DisplayName("empty")
    fun empty() {
        val line = ""
        assertThat(MarkLogicErrorLogLine.parse(line), `is`(line))
    }

    @Test
    @DisplayName("Java exception")
    fun javaException() {
        val lines = arrayOf(
            "WARNING: JNI local refs: zu, exceeds capacity: zu",
            "\tat java.lang.System.initProperties(Native Method)",
            "\tat java.lang.System.initializeSystemClass(System.java:1166)"
        )

        assertThat(MarkLogicErrorLogLine.parse(lines[0]), `is`(lines[0]))
        assertThat(MarkLogicErrorLogLine.parse(lines[1]), `is`(lines[1]))
        assertThat(MarkLogicErrorLogLine.parse(lines[2]), `is`(lines[2]))
    }

    @Test
    @DisplayName("simple message")
    fun simpleMessage() {
        val line = "2001-01-10 12:34:56.789 Info: Lorem ipsum dolor"
        val logLine = MarkLogicErrorLogLine.parse(line) as MarkLogicErrorLogLine

        assertThat(logLine.date, `is`("2001-01-10"))
        assertThat(logLine.time, `is`("12:34:56.789"))
        assertThat(logLine.logLevel, `is`("Info"))
        assertThat(logLine.message, `is`("Lorem ipsum dolor"))

        reset()
        logLine.print(this)
        assertThat(printed, `is`("[NORMAL_OUTPUT|$line]"))
    }
}