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
package uk.co.reecedunn.intellij.plugin.intellij.settings

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import uk.co.reecedunn.intellij.plugin.core.ui.layout.*
import uk.co.reecedunn.intellij.plugin.intellij.resources.XpmBundle
import uk.co.reecedunn.intellij.plugin.xpm.module.loader.XpmModuleLoaderSettings
import javax.swing.JComponent

class XpmModuleConfigurable(val project: Project) : Configurable {
    private val settings = XpmModuleLoaderSettings.getInstance(project)

    // region Configurable

    private lateinit var databasePath: TextFieldWithBrowseButton

    override fun getDisplayName(): String = XpmBundle.message("preferences.module.title")

    override fun createComponent(): JComponent? = panel {
        row {
            label(XpmBundle.message("preferences.module.database-path.label"), column)
            databasePath = textFieldWithBrowseButton(column.hgap().horizontal()) {
                val descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
                descriptor.title = XpmBundle.message("preferences.module.database-path.choose-path")
                addBrowseFolderListener(null, null, project, descriptor)
            }
        }
        row {
            spacer(column.vertical())
            spacer(column.horizontal())
        }
    }

    override fun isModified(): Boolean {
        if (databasePath.textField.text != settings.databasePath) return true
        return false
    }

    override fun apply() {
        settings.databasePath = databasePath.textField.text
    }

    override fun reset() {
        databasePath.textField.text = settings.databasePath
    }

    // endregion
}
