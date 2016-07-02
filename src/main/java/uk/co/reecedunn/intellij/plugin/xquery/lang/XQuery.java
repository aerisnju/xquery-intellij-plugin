/*
 * Copyright (C) 2016 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.lang;

import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

public class XQuery extends Language {
    public static final String[] MIME_TYPES = new String[] { "application/xquery" };

    public static final XQuery INSTANCE = new XQuery();

    private XQuery() {
        super("XQuery", "application/xquery");
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }

    @NotNull
    @Override
    public String[] getMimeTypes() {
        return MIME_TYPES;
    }
}
