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
package uk.co.reecedunn.intellij.plugin.xslt.tests.psi

import org.hamcrest.CoreMatchers.*
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.co.reecedunn.intellij.plugin.core.tests.assertion.assertThat
import uk.co.reecedunn.intellij.plugin.xslt.ast.saxon.SaxonArray
import uk.co.reecedunn.intellij.plugin.xslt.ast.saxon.SaxonArrayMember
import uk.co.reecedunn.intellij.plugin.xslt.ast.saxon.SaxonAssign
import uk.co.reecedunn.intellij.plugin.xslt.ast.saxon.SaxonDeepUpdate
import uk.co.reecedunn.intellij.plugin.xslt.ast.xml.XsltDirElemConstructor
import uk.co.reecedunn.intellij.plugin.xslt.ast.xslt.*
import uk.co.reecedunn.intellij.plugin.xslt.intellij.lang.XSLT
import uk.co.reecedunn.intellij.plugin.xslt.tests.parser.ParserTestCase

// NOTE: This class is private so the JUnit 4 test runner does not run the tests contained in it.
@DisplayName("XQuery IntelliJ Plugin - IntelliJ Program Structure Interface (PSI) - XSLT")
private class PluginPsiTest : ParserTestCase() {
    companion object {
        private const val SAXON_NAMESPACE = "http://saxon.sf.net/"
    }

    @Nested
    @DisplayName("Saxon")
    internal inner class Saxon {
        @Nested
        @DisplayName("saxon:array")
        internal inner class Array {
            @Test
            @DisplayName("hierarchy")
            fun hierarchy() {
                @Language("XML") val xml = """
                    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                                    xmlns:saxon="http://saxon.sf.net/" version="3.0" extension-element-prefixes="saxon">
                        <saxon:array/>
                    </xsl:stylesheet>
                """
                val psi = parse<SaxonArray>(xml, SAXON_NAMESPACE, "array")[0]

                assertThat(psi.parent, `is`(instanceOf(XsltStylesheet::class.java)))
                assertThat(psi.children.size, `is`(0))
                assertThat(psi.prevSibling, `is`(nullValue()))
                assertThat(psi.nextSibling, `is`(nullValue()))

                val parent = psi.parent!!
                assertThat(parent.children.size, `is`(1))
                assertThat(parent.children[0], `is`(sameInstance(psi)))
            }
        }

        @Nested
        @DisplayName("saxon:array-member")
        internal inner class ArrayMember {
            @Test
            @DisplayName("hierarchy")
            fun hierarchy() {
                @Language("XML") val xml = """
                    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                                    xmlns:saxon="http://saxon.sf.net/" version="3.0" extension-element-prefixes="saxon">
                        <saxon:array-member select="(1, 2, 3)"/>
                    </xsl:stylesheet>
                """
                val psi = parse<SaxonArrayMember>(xml, SAXON_NAMESPACE, "array-member")[0]

                assertThat(psi.parent, `is`(instanceOf(XsltStylesheet::class.java)))
                assertThat(psi.children.size, `is`(0))
                assertThat(psi.prevSibling, `is`(nullValue()))
                assertThat(psi.nextSibling, `is`(nullValue()))

                val parent = psi.parent!!
                assertThat(parent.children.size, `is`(1))
                assertThat(parent.children[0], `is`(sameInstance(psi)))
            }
        }

        @Nested
        @DisplayName("saxon:assign")
        internal inner class Assign {
            @Test
            @DisplayName("hierarchy")
            fun hierarchy() {
                @Language("XML") val xml = """
                    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                                    xmlns:saxon="http://saxon.sf.net/" version="3.0" extension-element-prefixes="saxon">
                        <saxon:assign name="lorem-ipsum">Ipsum</saxon:assign>
                    </xsl:stylesheet>
                """
                val psi = parse<SaxonAssign>(xml, SAXON_NAMESPACE, "assign")[0]

                assertThat(psi.parent, `is`(instanceOf(XsltStylesheet::class.java)))
                assertThat(psi.children.size, `is`(0))
                assertThat(psi.prevSibling, `is`(nullValue()))
                assertThat(psi.nextSibling, `is`(nullValue()))

                val parent = psi.parent!!
                assertThat(parent.children.size, `is`(1))
                assertThat(parent.children[0], `is`(sameInstance(psi)))
            }
        }

        @Nested
        @DisplayName("saxon:deep-update")
        internal inner class DeepUpdate {
            @Test
            @DisplayName("hierarchy")
            fun hierarchy() {
                @Language("XML") val xml = """
                    <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                                    xmlns:saxon="http://saxon.sf.net/" version="3.0" extension-element-prefixes="saxon">
                        <saxon:deep-update root="${'$'}m" select="?*[?lorem = 'ipsum']" action="()"/>
                    </xsl:stylesheet>
                """
                val psi = parse<SaxonDeepUpdate>(xml, SAXON_NAMESPACE, "deep-update")[0]

                assertThat(psi.parent, `is`(instanceOf(XsltStylesheet::class.java)))
                assertThat(psi.children.size, `is`(0))
                assertThat(psi.prevSibling, `is`(nullValue()))
                assertThat(psi.nextSibling, `is`(nullValue()))

                val parent = psi.parent!!
                assertThat(parent.children.size, `is`(1))
                assertThat(parent.children[0], `is`(sameInstance(psi)))
            }
        }
    }
}
