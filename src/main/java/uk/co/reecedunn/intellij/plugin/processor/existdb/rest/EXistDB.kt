/*
 * Copyright (C) 2018 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.processor.existdb.rest

import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.HttpClients
import uk.co.reecedunn.intellij.plugin.processor.query.ConnectionSettings
import uk.co.reecedunn.intellij.plugin.processor.query.MissingHostNameException
import uk.co.reecedunn.intellij.plugin.processor.query.QueryProcessor
import uk.co.reecedunn.intellij.plugin.processor.query.QueryProcessorInstanceManager

class EXistDB : QueryProcessorInstanceManager {
    override fun create(): QueryProcessor {
        // eXist-db does not provide support for running as an in-memory instance.
        throw UnsupportedOperationException()
    }

    override fun connect(settings: ConnectionSettings): QueryProcessor {
        if (settings.hostname.isEmpty())
            throw MissingHostNameException()

        val baseUrl = "http://${settings.hostname}:${settings.databasePort}/exist/rest"

        if (settings.username == null || settings.password == null) {
            return EXistDBQueryProcessor(baseUrl, HttpClients.createDefault())
        }

        val credentials = BasicCredentialsProvider()
        credentials.setCredentials(
            AuthScope(settings.hostname, settings.databasePort),
            UsernamePasswordCredentials(settings.username, settings.password)
        )
        return EXistDBQueryProcessor(baseUrl, HttpClients.custom().setDefaultCredentialsProvider(credentials).build())
    }
}
