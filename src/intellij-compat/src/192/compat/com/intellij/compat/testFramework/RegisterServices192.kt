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
package com.intellij.compat.testFramework

import com.intellij.openapi.extensions.ExtensionPointName

@Suppress("UnstableApiUsage")
fun PlatformLiteFixture.registerCodeStyleSettingsModifier() {
    try {
        val epClass = Class.forName("com.intellij.application.options.CodeStyleCachingUtil")
        val epname = epClass.getDeclaredField("CODE_STYLE_SETTINGS_MODIFIER_EP_NAME")
        epname.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        registerExtensionPoint(epname.get(null) as ExtensionPointName<Any>, epClass as Class<Any>)
    } catch (e: Exception) {
        // Don't register the extension point, as the associated class is not found.
    }
}
