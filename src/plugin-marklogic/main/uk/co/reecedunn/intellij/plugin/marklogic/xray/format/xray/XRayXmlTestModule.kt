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
package uk.co.reecedunn.intellij.plugin.marklogic.xray.format.xray

import uk.co.reecedunn.intellij.plugin.core.xml.XmlElement
import uk.co.reecedunn.intellij.plugin.marklogic.xray.test.XRayTest
import uk.co.reecedunn.intellij.plugin.marklogic.xray.test.XRayTestModule

class XRayXmlTestModule(private val module: XmlElement) : XRayTestModule {
    override val path: String
        get() = module.attribute("path")!!

    override val tests: Sequence<XRayTest>
        get() = module.children("xray:test").map { XRayXmlTest(it) }
}
