/*
 * Copyright (C) 2016-2019 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xquery.parser

import com.intellij.psi.tree.IElementType
import uk.co.reecedunn.intellij.plugin.core.parser.ICompositeElementType
import uk.co.reecedunn.intellij.plugin.intellij.lang.XQuery
import uk.co.reecedunn.intellij.plugin.xquery.psi.impl.xquery.*

object XQueryElementType2 {
    // region XQuery 1.0

    val QNAME: IElementType = ICompositeElementType(
        "XQUERY_QNAME",
        XQueryQNamePsiImpl::class.java,
        XQuery
    )

    val DIR_ELEM_CONSTRUCTOR: IElementType = ICompositeElementType(
        "XQUERY_DIR_ELEM_CONSTRUCTOR",
        XQueryDirElemConstructorPsiImpl::class.java,
        XQuery
    )

    // endregion
    // region XQuery 3.0

    val PREFIX: IElementType = ICompositeElementType(
        "XQUERY_PREFIX",
        XQueryPrefixPsiImpl::class.java,
        XQuery
    )

    // endregion
}
