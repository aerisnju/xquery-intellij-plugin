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
package uk.co.reecedunn.intellij.plugin.marklogic.tests.query.rest

import java.net.URLEncoder

@Suppress("UNCHECKED_CAST")
fun List<Pair<String, Any>>.toFormParamString(): String {
    return joinToString("&") {
        val value = when (it.second) {
            is String -> it.second as String
            is List<*> -> (it.second as List<Pair<String, String>>).toJsonString()
            else -> it.second.toString()
        }
        "${it.first}=${URLEncoder.encode(value, "UTF-8")}"
    }
}

fun List<Pair<String, String>>.toJsonString(): String {
    return joinToString(",", "{", "}") {
        "\"${it.first}\":\"${it.second}\""
    }
}
