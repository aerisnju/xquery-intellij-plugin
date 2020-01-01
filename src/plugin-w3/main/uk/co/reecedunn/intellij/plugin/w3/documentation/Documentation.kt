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
package uk.co.reecedunn.intellij.plugin.w3.documentation

import uk.co.reecedunn.intellij.plugin.xdm.documentation.XdmDocumentationSource
import uk.co.reecedunn.intellij.plugin.xdm.documentation.XdmDocumentationSourceProvider
import uk.co.reecedunn.intellij.plugin.xdm.lang.XdmSpecificationType

private data class W3CSpecificationDocument(
    val type: XdmSpecificationType,
    override val href: String,
    val id: String,
    override val version: String
) : XdmDocumentationSource {
    override val name: String = type.name

    override val path: String = "w3/${type.id}-$id.html"
}

object FunctionsAndOperatorsDocumentation : XdmSpecificationType, XdmDocumentationSourceProvider {
    // region Specifications

    val WD_1_0_20030502: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2003/WD-xpath-functions-20030502/",
        "1.0-20030502", "1.0 (Working Draft 02 May 2003)" // Used by the MarkLogic 0.9-ml XQuery version.
    )

    val REC_1_0_20070123: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2007/REC-xpath-functions-20070123/",
        "1.0-20070123", "1.0 (First Edition)"
    )

    val REC_1_0_20101214: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2010/REC-xpath-functions-20101214/",
        "1.0-20101214", "1.0 (Second Edition)"
    )

    val WD_3_0_20111213: XdmDocumentationSource = W3CSpecificationDocument(
        this, "http://www.w3.org/TR/2011/WD-xpath-functions-30-20111213/",
        "3.0-20111213", "3.0 (Working Draft 13 Dec 2011)" // Used by the MarkLogic 1.0-ml XQuery version.
    )

    val REC_3_0_20140408: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2014/REC-xpath-functions-30-20140408/",
        "3.0-20140408", "3.0"
    )

    val REC_3_1_20170321: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2017/REC-xpath-functions-31-20170321/",
        "3.1-20170321", "3.1"
    )

    // endregion
    // region XdmSpecificationType

    override val id: String = "xpath-functions"

    override val name = "XQuery and XPath Functions and Operators"

    // endregion
    // region XdmDocumentationSourceProvider

    override val sources: List<XdmDocumentationSource> = listOf(
        WD_1_0_20030502,
        REC_1_0_20070123,
        REC_1_0_20101214,
        WD_3_0_20111213,
        REC_3_0_20140408,
        REC_3_1_20170321
    )

    // endregion
}

object XsltDocumentation : XdmSpecificationType, XdmDocumentationSourceProvider {
    // region Specifications

    val REC_1_0_19991116: XdmDocumentationSource = W3CSpecificationDocument(
        this, "http://www.w3.org/TR/1999/REC-xslt-19991116/", "1.0-19991116", "1.0"
    )

    val REC_2_0_20070123: XdmDocumentationSource = W3CSpecificationDocument(
        this, "http://www.w3.org/TR/2007/REC-xslt20-20070123/", "2.0-20070123", "2.0"
    )

    val REC_3_0_20170608: XdmDocumentationSource = W3CSpecificationDocument(
        this, "https://www.w3.org/TR/2017/REC-xslt-30-20170608/", "3.0-20170608", "3.0"
    )

    // endregion
    // region XdmSpecificationType

    override val id: String = "xslt"

    override val name = "XSL Transformations (XSLT)"

    // endregion
    // region XdmDocumentationSourceProvider

    override val sources: List<XdmDocumentationSource> = listOf(
        REC_1_0_19991116,
        REC_2_0_20070123,
        REC_3_0_20170608
    )

    // endregion
}
