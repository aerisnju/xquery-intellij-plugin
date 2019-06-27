/*
 * Copyright (C) 2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.completion

import uk.co.reecedunn.intellij.plugin.core.completion.CompletionContributorEx
import uk.co.reecedunn.intellij.plugin.xpath.completion.filters.XPathItemTypeFilter
import uk.co.reecedunn.intellij.plugin.xpath.completion.filters.XPathKindTestFilter
import uk.co.reecedunn.intellij.plugin.xpath.completion.filters.XPathSequenceTypeFilter
import uk.co.reecedunn.intellij.plugin.xpath.completion.providers.XPathItemTypeProvider
import uk.co.reecedunn.intellij.plugin.xpath.completion.providers.XPathKindTestProvider
import uk.co.reecedunn.intellij.plugin.xpath.completion.providers.XPathSequenceTypeProvider
import uk.co.reecedunn.intellij.plugin.xquery.completion.property.XPathVersion
import uk.co.reecedunn.intellij.plugin.xquery.completion.property.XQueryProductVersion
import uk.co.reecedunn.intellij.plugin.xquery.completion.providers.XQueryKindTestProvider

class XQueryCompletionContributor : CompletionContributorEx() {
    init {
        // XQuery 3.1 EBNF (184) SequenceType
        builder().withFilter(XPathSequenceTypeFilter).withProperty(XPathVersion)
            .addCompletions(XPathSequenceTypeProvider)

        // XQuery 3.1 EBNF (186) ItemType
        builder().withFilter(XPathItemTypeFilter).withProperty(XPathVersion).addCompletions(XPathItemTypeProvider)

        // XQuery 3.1 EBNF (188) KindTest
        builder().withFilter(XPathKindTestFilter)
            .withProperty(XPathVersion).withProperty(XQueryProductVersion)
            .addCompletions(XQueryKindTestProvider).addCompletions(XPathKindTestProvider)
    }
}
