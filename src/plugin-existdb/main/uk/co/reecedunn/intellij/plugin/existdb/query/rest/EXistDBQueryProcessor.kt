/*
 * Copyright (C) 2018-2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.existdb.query.rest

import com.intellij.lang.Language
import com.intellij.openapi.vfs.VirtualFile
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.entity.StringEntity
import uk.co.reecedunn.intellij.plugin.core.async.*
import uk.co.reecedunn.intellij.plugin.core.vfs.decode
import uk.co.reecedunn.intellij.plugin.core.xml.XmlDocument
import uk.co.reecedunn.intellij.plugin.existdb.resources.EXistDBQueries
import uk.co.reecedunn.intellij.plugin.intellij.lang.XQuery
import uk.co.reecedunn.intellij.plugin.processor.query.*
import uk.co.reecedunn.intellij.plugin.processor.query.http.HttpConnection

internal class EXistDBQueryProcessor(private val baseUri: String, private val connection: HttpConnection) :
    RunnableQueryProvider {

    override val version: ExecutableOnPooledThread<String> by cached {
        createRunnableQuery(EXistDBQueries.Version, XQuery).use { query ->
            pooled_thread { query.run() }.then { results -> results.results.first().value as String }
        }
    }

    override val servers: List<String> = listOf<String>()

    override val databases: ExecutableOnPooledThread<List<String>> = local_thread {
        listOf<String>()
    }

    override fun createRunnableQuery(query: VirtualFile, language: Language): RunnableQuery {
        return when (language) {
            XQuery -> {
                val xml = XmlDocument.parse(EXistDBQueries.PostQueryTemplate, mapOf())
                xml.root.children("text").first().appendChild(xml.doc.createCDATASection(query.decode()!!))

                val builder = RequestBuilder.post("$baseUri/db")
                builder.entity = StringEntity(xml.toXmlString())
                EXistDBQuery(builder, query, connection)
            }
            else -> throw UnsupportedQueryType(language)
        }
    }

    override fun close() = connection.close()
}
