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
package com.intellij.compat.testFramework

import com.intellij.mock.MockProject
import com.intellij.mock.MockProjectEx
import com.intellij.openapi.project.Project
import com.intellij.pom.PomModel
import com.intellij.pom.PomTransaction
import com.intellij.pom.core.impl.PomModelImpl
import com.intellij.pom.tree.TreeAspect

private class MockPomModelImpl(project: Project) : PomModelImpl(project) {
    override fun runTransaction(transaction: PomTransaction) {
    }
}

@Suppress("UnstableApiUsage")
fun registerPomModel(project: MockProject) {
    val pomModel = MockPomModelImpl(project)
    project.registerService(PomModel::class.java, pomModel)
    TreeAspect(pomModel)
}

@Suppress("unused")
fun MockProjectEx.registerCodeStyleCachingService() {
}