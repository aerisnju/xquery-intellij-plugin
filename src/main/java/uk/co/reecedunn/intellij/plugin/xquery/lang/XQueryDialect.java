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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.reecedunn.intellij.plugin.xquery.XQueryBundle;

public enum XQueryDialect {
    XQUERY_0_9_MARKLOGIC_3_2("0.9-ml/3.2", XQueryVersion.XQUERY_0_9_MARKLOGIC, XQueryVendor.MARKLOGIC, XQueryBundle.message("xquery.dialect.description.0.9-ml/3.2"), "https://docs.marklogic.com/guide/xquery/dialects#id_65735"),
    XQUERY_1_0_W3C("1.0/W3C", XQueryVersion.XQUERY_1_0, XQueryVendor.W3C, XQueryBundle.message("xquery.dialect.description.1.0/W3C"), "https://www.w3.org/TR/2007/REC-xquery-20070123/"),
    XQUERY_1_0_MARKLOGIC_5("1.0-ml/5", XQueryVersion.XQUERY_1_0_MARKLOGIC, XQueryVendor.MARKLOGIC, XQueryBundle.message("xquery.dialect.description.1.0-ml/5"), "https://docs.marklogic.com/5.0/guide/xquery/dialects"),
    XQUERY_1_0_MARKLOGIC_6("1.0-ml/6", XQueryVersion.XQUERY_1_0_MARKLOGIC, XQueryVendor.MARKLOGIC, XQueryBundle.message("xquery.dialect.description.1.0-ml/6"), "https://docs.marklogic.com/6.0/guide/xquery/dialects"),
    XQUERY_1_0_MARKLOGIC_7("1.0-ml/7", XQueryVersion.XQUERY_1_0_MARKLOGIC, XQueryVendor.MARKLOGIC, XQueryBundle.message("xquery.dialect.description.1.0-ml/7"), "https://docs.marklogic.com/7.0/guide/xquery/dialects"),
    XQUERY_1_0_MARKLOGIC_8("1.0-ml/8", XQueryVersion.XQUERY_1_0_MARKLOGIC, XQueryVendor.MARKLOGIC, XQueryBundle.message("xquery.dialect.description.1.0-ml/8"), "https://docs.marklogic.com/8.0/guide/xquery/dialects"),
    XQUERY_3_0_W3C("3.0/W3C", XQueryVersion.XQUERY_3_0, XQueryVendor.W3C, XQueryBundle.message("xquery.dialect.description.3.0/W3C"), "https://www.w3.org/TR/2014/REC-xquery-30-20140408/"),
    XQUERY_3_1_W3C("3.1/W3C", XQueryVersion.XQUERY_3_1, XQueryVendor.W3C, XQueryBundle.message("xquery.dialect.description.3.1/W3C"), "https://www.w3.org/TR/2015/CR-xquery-31-20151217/");

    private final String mName;
    private final XQueryVersion mVersion;
    private final XQueryVendor mVendor;
    private final String mDescription;
    private final String mReference;

    XQueryDialect(@NotNull String name, @NotNull XQueryVersion version, @NotNull XQueryVendor vendor, @NotNull String description, @NotNull String reference) {
        mName = name;
        mVersion = version;
        mVendor = vendor;
        mDescription = description;
        mReference = reference;
    }

    @NotNull
    public String getName() {
        return mName;
    }

    @NotNull
    public XQueryVersion getLanguageVersion() {
        return mVersion;
    }

    @NotNull
    public XQueryVendor getVendor() {
        return mVendor;
    }

    @NotNull
    public String getDescription() {
        return mDescription;
    }

    @NotNull
    public String getReference() {
        return mReference;
    }

    @Nullable
    public static XQueryDialect parse(@Nullable String value) {
        if ("0.9-ml/3.2".equals(value)) return XQUERY_0_9_MARKLOGIC_3_2;
        if ("1.0/W3C".equals(value)) return XQUERY_1_0_W3C;
        if ("1.0-ml/5".equals(value)) return XQUERY_1_0_MARKLOGIC_5;
        if ("1.0-ml/6".equals(value)) return XQUERY_1_0_MARKLOGIC_6;
        if ("1.0-ml/7".equals(value)) return XQUERY_1_0_MARKLOGIC_7;
        if ("1.0-ml/8".equals(value)) return XQUERY_1_0_MARKLOGIC_8;
        if ("3.0/W3C".equals(value)) return XQUERY_3_0_W3C;
        if ("3.1/W3C".equals(value)) return XQUERY_3_1_W3C;
        return null;
    }

    @NotNull
    @Override
    public String toString() {
        return getName();
    }
}
