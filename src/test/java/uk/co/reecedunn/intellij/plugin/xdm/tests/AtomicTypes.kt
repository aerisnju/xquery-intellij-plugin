/*
 * Copyright (C) 2017-2018 Reece H. Dunn
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
package uk.co.reecedunn.intellij.plugin.xdm.tests

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.Test
import uk.co.reecedunn.intellij.plugin.core.tests.assertion.assertThat
import uk.co.reecedunn.intellij.plugin.xdm.*
import uk.co.reecedunn.intellij.plugin.xdm.model.XdmSequenceType
import uk.co.reecedunn.intellij.plugin.xdm.model.XmlSchemaType

class AtomicTypes {
    @Test
    fun testXsUntypedAtomic() {
        assertThat(XsUntypedAtomic.typeName?.declaration, `is`(nullValue()))

        assertThat(XsUntypedAtomic.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsUntypedAtomic.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsUntypedAtomic.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsUntypedAtomic.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsUntypedAtomic.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsUntypedAtomic.typeName?.localName?.staticValue as String, `is`("untypedAtomic"))

        assertThat(XsUntypedAtomic.baseType, `is`(XsAnyAtomicType))
        assertThat(XsUntypedAtomic.isPrimitive, `is`(true)) // XDM casting rules

        assertThat(XsUntypedAtomic.toString(), `is`("xs:untypedAtomic"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "untypedAtomic").toXmlSchemaType(),
                `is`(XsUntypedAtomic))
    }

    @Test
    fun testXsDateTime() {
        assertThat(XsDateTime.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDateTime.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDateTime.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDateTime.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDateTime.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDateTime.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDateTime.typeName?.localName?.staticValue as String, `is`("dateTime"))

        assertThat(XsDateTime.baseType, `is`(XsAnyAtomicType))
        assertThat(XsDateTime.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsDateTime.toString(), `is`("xs:dateTime"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "dateTime").toXmlSchemaType(),
                `is`(XsDateTime))
    }

    @Test
    fun testXsDateTimeStamp() {
        assertThat(XsDateTimeStamp.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDateTimeStamp.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDateTimeStamp.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDateTimeStamp.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDateTimeStamp.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDateTimeStamp.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDateTimeStamp.typeName?.localName?.staticValue as String, `is`("dateTimeStamp"))

        assertThat(XsDateTimeStamp.baseType, `is`(XsDateTime))
        assertThat(XsDateTimeStamp.isPrimitive, `is`(false))

        assertThat(XsDateTimeStamp.toString(), `is`("xs:dateTimeStamp"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "dateTimeStamp").toXmlSchemaType(),
                `is`(XsDateTimeStamp))
    }

    @Test
    fun testXsDate() {
        assertThat(XsDate.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDate.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDate.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDate.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDate.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDate.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDate.typeName?.localName?.staticValue as String, `is`("date"))

        assertThat(XsDate.baseType, `is`(XsAnyAtomicType))
        assertThat(XsDate.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsDate.toString(), `is`("xs:date"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "date").toXmlSchemaType(),
                `is`(XsDate))
    }

    @Test
    fun testXsTime() {
        assertThat(XsTime.typeName?.declaration, `is`(nullValue()))

        assertThat(XsTime.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsTime.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsTime.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsTime.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsTime.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsTime.typeName?.localName?.staticValue as String, `is`("time"))

        assertThat(XsTime.baseType, `is`(XsAnyAtomicType))
        assertThat(XsTime.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsTime.toString(), `is`("xs:time"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "time").toXmlSchemaType(),
                `is`(XsTime))
    }

    @Test
    fun testXsDuration() {
        assertThat(XsDuration.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDuration.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDuration.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDuration.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDuration.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDuration.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDuration.typeName?.localName?.staticValue as String, `is`("duration"))

        assertThat(XsDuration.baseType, `is`(XsAnyAtomicType))
        assertThat(XsDuration.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsDuration.toString(), `is`("xs:duration"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "duration").toXmlSchemaType(),
                `is`(XsDuration))
    }

    @Test
    fun testXsYearMonthDuration() {
        assertThat(XsYearMonthDuration.typeName?.declaration, `is`(nullValue()))

        assertThat(XsYearMonthDuration.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsYearMonthDuration.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsYearMonthDuration.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsYearMonthDuration.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsYearMonthDuration.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsYearMonthDuration.typeName?.localName?.staticValue as String, `is`("yearMonthDuration"))

        assertThat(XsYearMonthDuration.baseType, `is`(XsDuration))
        assertThat(XsYearMonthDuration.isPrimitive, `is`(true)) // XDM casting rules

        assertThat(XsYearMonthDuration.toString(), `is`("xs:yearMonthDuration"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "yearMonthDuration").toXmlSchemaType(),
                `is`(XsYearMonthDuration))
    }

    @Test
    fun testXsDayTimeDuration() {
        assertThat(XsDayTimeDuration.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDayTimeDuration.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDayTimeDuration.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDayTimeDuration.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDayTimeDuration.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDayTimeDuration.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDayTimeDuration.typeName?.localName?.staticValue as String, `is`("dayTimeDuration"))

        assertThat(XsDayTimeDuration.baseType, `is`(XsDuration))
        assertThat(XsDayTimeDuration.isPrimitive, `is`(true)) // XDM casting rules

        assertThat(XsDayTimeDuration.toString(), `is`("xs:dayTimeDuration"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "dayTimeDuration").toXmlSchemaType(),
                `is`(XsDayTimeDuration))
    }

    @Test
    fun testXsFloat() {
        assertThat(XsFloat.typeName?.declaration, `is`(nullValue()))

        assertThat(XsFloat.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsFloat.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsFloat.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsFloat.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsFloat.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsFloat.typeName?.localName?.staticValue as String, `is`("float"))

        assertThat(XsFloat.baseType, `is`(XsAnyAtomicType))
        assertThat(XsFloat.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsFloat.toString(), `is`("xs:float"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "float").toXmlSchemaType(),
                `is`(XsFloat))
    }

    @Test
    fun testXsDouble() {
        assertThat(XsDouble.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDouble.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDouble.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDouble.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDouble.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDouble.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDouble.typeName?.localName?.staticValue as String, `is`("double"))

        assertThat(XsDouble.baseType, `is`(XsAnyAtomicType))
        assertThat(XsDouble.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsDouble.toString(), `is`("xs:double"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "double").toXmlSchemaType(),
                `is`(XsDouble))
    }

    @Test
    fun testXsDecimal() {
        assertThat(XsDecimal.typeName?.declaration, `is`(nullValue()))

        assertThat(XsDecimal.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsDecimal.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsDecimal.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsDecimal.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsDecimal.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsDecimal.typeName?.localName?.staticValue as String, `is`("decimal"))

        assertThat(XsDecimal.baseType, `is`(XsAnyAtomicType))
        assertThat(XsDecimal.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsDecimal.toString(), `is`("xs:decimal"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "decimal").toXmlSchemaType(),
                `is`(XsDecimal))
    }

    @Test
    fun testXsInteger() {
        assertThat(XsInteger.typeName?.declaration, `is`(nullValue()))

        assertThat(XsInteger.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsInteger.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsInteger.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsInteger.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsInteger.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsInteger.typeName?.localName?.staticValue as String, `is`("integer"))

        assertThat(XsInteger.baseType, `is`(XsDecimal))
        assertThat(XsInteger.isPrimitive, `is`(true)) // XDM casting rules

        assertThat(XsInteger.toString(), `is`("xs:integer"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "integer").toXmlSchemaType(),
                `is`(XsInteger))
    }

    @Test
    fun testXsNonPositiveInteger() {
        assertThat(XsNonPositiveInteger.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNonPositiveInteger.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNonPositiveInteger.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNonPositiveInteger.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNonPositiveInteger.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNonPositiveInteger.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNonPositiveInteger.typeName?.localName?.staticValue as String, `is`("nonPositiveInteger"))

        assertThat(XsNonPositiveInteger.baseType, `is`(XsInteger))
        assertThat(XsNonPositiveInteger.isPrimitive, `is`(false))

        assertThat(XsNonPositiveInteger.toString(), `is`("xs:nonPositiveInteger"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "nonPositiveInteger").toXmlSchemaType(),
                `is`(XsNonPositiveInteger))
    }

    @Test
    fun testXsNegativeInteger() {
        assertThat(XsNegativeInteger.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNegativeInteger.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNegativeInteger.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNegativeInteger.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNegativeInteger.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNegativeInteger.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNegativeInteger.typeName?.localName?.staticValue as String, `is`("negativeInteger"))

        assertThat(XsNegativeInteger.baseType, `is`(XsNonPositiveInteger))
        assertThat(XsNegativeInteger.isPrimitive, `is`(false))

        assertThat(XsNegativeInteger.toString(), `is`("xs:negativeInteger"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "negativeInteger").toXmlSchemaType(),
                `is`(XsNegativeInteger))
    }

    @Test
    fun testXsLong() {
        assertThat(XsLong.typeName?.declaration, `is`(nullValue()))

        assertThat(XsLong.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsLong.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsLong.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsLong.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsLong.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsLong.typeName?.localName?.staticValue as String, `is`("long"))

        assertThat(XsLong.baseType, `is`(XsInteger))
        assertThat(XsLong.isPrimitive, `is`(false))

        assertThat(XsLong.toString(), `is`("xs:long"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "long").toXmlSchemaType(),
                `is`(XsLong))
    }

    @Test
    fun testXsInt() {
        assertThat(XsInt.typeName?.declaration, `is`(nullValue()))

        assertThat(XsInt.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsInt.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsInt.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsInt.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsInt.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsInt.typeName?.localName?.staticValue as String, `is`("int"))

        assertThat(XsInt.baseType, `is`(XsLong))
        assertThat(XsInt.isPrimitive, `is`(false))

        assertThat(XsInt.toString(), `is`("xs:int"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "int").toXmlSchemaType(),
                `is`(XsInt))
    }

    @Test
    fun testXsShort() {
        assertThat(XsShort.typeName?.declaration, `is`(nullValue()))

        assertThat(XsShort.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsShort.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsShort.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsShort.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsShort.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsShort.typeName?.localName?.staticValue as String, `is`("short"))

        assertThat(XsShort.baseType, `is`(XsInt))
        assertThat(XsShort.isPrimitive, `is`(false))

        assertThat(XsShort.toString(), `is`("xs:short"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "short").toXmlSchemaType(),
                `is`(XsShort))
    }

    @Test
    fun testXsByte() {
        assertThat(XsByte.typeName?.declaration, `is`(nullValue()))

        assertThat(XsByte.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsByte.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsByte.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsByte.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsByte.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsByte.typeName?.localName?.staticValue as String, `is`("byte"))

        assertThat(XsByte.baseType, `is`(XsShort))
        assertThat(XsByte.isPrimitive, `is`(false))

        assertThat(XsByte.toString(), `is`("xs:byte"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "byte").toXmlSchemaType(),
                `is`(XsByte))
    }

    @Test
    fun testXsNonNegativeInteger() {
        assertThat(XsNonNegativeInteger.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNonNegativeInteger.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNonNegativeInteger.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNonNegativeInteger.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNonNegativeInteger.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNonNegativeInteger.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNonNegativeInteger.typeName?.localName?.staticValue as String, `is`("nonNegativeInteger"))

        assertThat(XsNonNegativeInteger.baseType, `is`(XsInteger))
        assertThat(XsNonNegativeInteger.isPrimitive, `is`(false))

        assertThat(XsNonNegativeInteger.toString(), `is`("xs:nonNegativeInteger"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger").toXmlSchemaType(),
                `is`(XsNonNegativeInteger))
    }

    @Test
    fun testXsUnsignedLong() {
        assertThat(XsUnsignedLong.typeName?.declaration, `is`(nullValue()))

        assertThat(XsUnsignedLong.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedLong.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsUnsignedLong.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsUnsignedLong.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsUnsignedLong.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedLong.typeName?.localName?.staticValue as String, `is`("unsignedLong"))

        assertThat(XsUnsignedLong.baseType, `is`(XsNonNegativeInteger))
        assertThat(XsUnsignedLong.isPrimitive, `is`(false))

        assertThat(XsUnsignedLong.toString(), `is`("xs:unsignedLong"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "unsignedLong").toXmlSchemaType(),
                `is`(XsUnsignedLong))
    }

    @Test
    fun testXsUnsignedInt() {
        assertThat(XsUnsignedInt.typeName?.declaration, `is`(nullValue()))

        assertThat(XsUnsignedInt.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedInt.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsUnsignedInt.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsUnsignedInt.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsUnsignedInt.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedInt.typeName?.localName?.staticValue as String, `is`("unsignedInt"))

        assertThat(XsUnsignedInt.baseType, `is`(XsUnsignedLong))
        assertThat(XsUnsignedInt.isPrimitive, `is`(false))

        assertThat(XsUnsignedInt.toString(), `is`("xs:unsignedInt"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "unsignedInt").toXmlSchemaType(),
                `is`(XsUnsignedInt))
    }

    @Test
    fun testXsUnsignedShort() {
        assertThat(XsUnsignedShort.typeName?.declaration, `is`(nullValue()))

        assertThat(XsUnsignedShort.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedShort.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsUnsignedShort.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsUnsignedShort.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsUnsignedShort.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedShort.typeName?.localName?.staticValue as String, `is`("unsignedShort"))

        assertThat(XsUnsignedShort.baseType, `is`(XsUnsignedInt))
        assertThat(XsUnsignedShort.isPrimitive, `is`(false))

        assertThat(XsUnsignedShort.toString(), `is`("xs:unsignedShort"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "unsignedShort").toXmlSchemaType(),
                `is`(XsUnsignedShort))
    }

    @Test
    fun testXsUnsignedByte() {
        assertThat(XsUnsignedByte.typeName?.declaration, `is`(nullValue()))

        assertThat(XsUnsignedByte.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedByte.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsUnsignedByte.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsUnsignedByte.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsUnsignedByte.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsUnsignedByte.typeName?.localName?.staticValue as String, `is`("unsignedByte"))

        assertThat(XsUnsignedByte.baseType, `is`(XsUnsignedShort))
        assertThat(XsUnsignedByte.isPrimitive, `is`(false))

        assertThat(XsUnsignedByte.toString(), `is`("xs:unsignedByte"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "unsignedByte").toXmlSchemaType(),
                `is`(XsUnsignedByte))
    }

    @Test
    fun testXsPositiveInteger() {
        assertThat(XsPositiveInteger.typeName?.declaration, `is`(nullValue()))

        assertThat(XsPositiveInteger.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsPositiveInteger.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsPositiveInteger.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsPositiveInteger.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsPositiveInteger.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsPositiveInteger.typeName?.localName?.staticValue as String, `is`("positiveInteger"))

        assertThat(XsPositiveInteger.baseType, `is`(XsNonNegativeInteger))
        assertThat(XsPositiveInteger.isPrimitive, `is`(false))

        assertThat(XsPositiveInteger.toString(), `is`("xs:positiveInteger"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "positiveInteger").toXmlSchemaType(),
                `is`(XsPositiveInteger))
    }

    @Test
    fun testXsGYearMonth() {
        assertThat(XsGYearMonth.typeName?.declaration, `is`(nullValue()))

        assertThat(XsGYearMonth.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsGYearMonth.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsGYearMonth.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsGYearMonth.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsGYearMonth.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsGYearMonth.typeName?.localName?.staticValue as String, `is`("gYearMonth"))

        assertThat(XsGYearMonth.baseType, `is`(XsAnyAtomicType))
        assertThat(XsGYearMonth.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsGYearMonth.toString(), `is`("xs:gYearMonth"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "gYearMonth").toXmlSchemaType(),
                `is`(XsGYearMonth))
    }

    @Test
    fun testXsGYear() {
        assertThat(XsGYear.typeName?.declaration, `is`(nullValue()))

        assertThat(XsGYear.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsGYear.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsGYear.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsGYear.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsGYear.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsGYear.typeName?.localName?.staticValue as String, `is`("gYear"))

        assertThat(XsGYear.baseType, `is`(XsAnyAtomicType))
        assertThat(XsGYear.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsGYear.toString(), `is`("xs:gYear"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "gYear").toXmlSchemaType(),
                `is`(XsGYear))
    }

    @Test
    fun testXsGMonthDay() {
        assertThat(XsGMonthDay.typeName?.declaration, `is`(nullValue()))

        assertThat(XsGMonthDay.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsGMonthDay.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsGMonthDay.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsGMonthDay.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsGMonthDay.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsGMonthDay.typeName?.localName?.staticValue as String, `is`("gMonthDay"))

        assertThat(XsGMonthDay.baseType, `is`(XsAnyAtomicType))
        assertThat(XsGMonthDay.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsGMonthDay.toString(), `is`("xs:gMonthDay"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "gMonthDay").toXmlSchemaType(),
                `is`(XsGMonthDay))
    }

    @Test
    fun testXsGDay() {
        assertThat(XsGDay.typeName?.declaration, `is`(nullValue()))

        assertThat(XsGDay.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsGDay.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsGDay.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsGDay.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsGDay.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsGDay.typeName?.localName?.staticValue as String, `is`("gDay"))

        assertThat(XsGDay.baseType, `is`(XsAnyAtomicType))
        assertThat(XsGDay.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsGDay.toString(), `is`("xs:gDay"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "gDay").toXmlSchemaType(),
                `is`(XsGDay))
    }

    @Test
    fun testXsGMonth() {
        assertThat(XsGMonth.typeName?.declaration, `is`(nullValue()))

        assertThat(XsGMonth.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsGMonth.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsGMonth.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsGMonth.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsGMonth.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsGMonth.typeName?.localName?.staticValue as String, `is`("gMonth"))

        assertThat(XsGMonth.baseType, `is`(XsAnyAtomicType))
        assertThat(XsGMonth.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsGMonth.toString(), `is`("xs:gMonth"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "gMonth").toXmlSchemaType(),
                `is`(XsGMonth))
    }

    @Test
    fun testXsString() {
        assertThat(XsString.typeName?.declaration, `is`(nullValue()))

        assertThat(XsString.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsString.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsString.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsString.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsString.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsString.typeName?.localName?.staticValue as String, `is`("string"))

        assertThat(XsString.baseType, `is`(XsAnyAtomicType))
        assertThat(XsString.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsString.toString(), `is`("xs:string"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "string").toXmlSchemaType(),
                `is`(XsString))
    }

    @Test
    fun testXsNormalizedString() {
        assertThat(XsNormalizedString.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNormalizedString.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNormalizedString.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNormalizedString.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNormalizedString.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNormalizedString.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNormalizedString.typeName?.localName?.staticValue as String, `is`("normalizedString"))

        assertThat(XsNormalizedString.baseType, `is`(XsString))
        assertThat(XsNormalizedString.isPrimitive, `is`(false))

        assertThat(XsNormalizedString.toString(), `is`("xs:normalizedString"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "normalizedString").toXmlSchemaType(),
                `is`(XsNormalizedString))
    }

    @Test
    fun testXsToken() {
        assertThat(XsToken.typeName?.declaration, `is`(nullValue()))

        assertThat(XsToken.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsToken.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsToken.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsToken.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsToken.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsToken.typeName?.localName?.staticValue as String, `is`("token"))

        assertThat(XsToken.baseType, `is`(XsNormalizedString))
        assertThat(XsToken.isPrimitive, `is`(false))

        assertThat(XsToken.toString(), `is`("xs:token"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "token").toXmlSchemaType(),
                `is`(XsToken))
    }

    @Test
    fun testXsLanguage() {
        assertThat(XsLanguage.typeName?.declaration, `is`(nullValue()))

        assertThat(XsLanguage.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsLanguage.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsLanguage.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsLanguage.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsLanguage.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsLanguage.typeName?.localName?.staticValue as String, `is`("language"))

        assertThat(XsLanguage.baseType, `is`(XsToken))
        assertThat(XsLanguage.isPrimitive, `is`(false))

        assertThat(XsLanguage.toString(), `is`("xs:language"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "language").toXmlSchemaType(),
                `is`(XsLanguage))
    }

    @Test
    fun testXsNMTOKEN() {
        assertThat(XsNMTOKEN.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNMTOKEN.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNMTOKEN.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNMTOKEN.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNMTOKEN.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNMTOKEN.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNMTOKEN.typeName?.localName?.staticValue as String, `is`("NMTOKEN"))

        assertThat(XsNMTOKEN.baseType, `is`(XsToken))
        assertThat(XsNMTOKEN.isPrimitive, `is`(false))

        assertThat(XsNMTOKEN.toString(), `is`("xs:NMTOKEN"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "NMTOKEN").toXmlSchemaType(),
                `is`(XsNMTOKEN))
    }

    @Test
    fun testXsName() {
        assertThat(XsName.typeName?.declaration, `is`(nullValue()))

        assertThat(XsName.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsName.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsName.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsName.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsName.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsName.typeName?.localName?.staticValue as String, `is`("Name"))

        assertThat(XsName.baseType, `is`(XsToken))
        assertThat(XsName.isPrimitive, `is`(false))

        assertThat(XsName.toString(), `is`("xs:Name"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "Name").toXmlSchemaType(),
                `is`(XsName))
    }

    @Test
    fun testXsNCName() {
        assertThat(XsNCName.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNCName.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNCName.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNCName.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNCName.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNCName.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNCName.typeName?.localName?.staticValue as String, `is`("NCName"))

        assertThat(XsNCName.baseType, `is`(XsName))
        assertThat(XsNCName.isPrimitive, `is`(false))

        assertThat(XsNCName.toString(), `is`("xs:NCName"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "NCName").toXmlSchemaType(),
                `is`(XsNCName))
    }

    @Test
    fun testXsID() {
        assertThat(XsID.typeName?.declaration, `is`(nullValue()))

        assertThat(XsID.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsID.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsID.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsID.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsID.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsID.typeName?.localName?.staticValue as String, `is`("ID"))

        assertThat(XsID.baseType, `is`(XsNCName))
        assertThat(XsID.isPrimitive, `is`(false))

        assertThat(XsID.toString(), `is`("xs:ID"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "ID").toXmlSchemaType(),
                `is`(XsID))
    }

    @Test
    fun testXsIDREF() {
        assertThat(XsIDREF.typeName?.declaration, `is`(nullValue()))

        assertThat(XsIDREF.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsIDREF.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsIDREF.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsIDREF.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsIDREF.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsIDREF.typeName?.localName?.staticValue as String, `is`("IDREF"))

        assertThat(XsIDREF.baseType, `is`(XsNCName))
        assertThat(XsIDREF.isPrimitive, `is`(false))

        assertThat(XsIDREF.toString(), `is`("xs:IDREF"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "IDREF").toXmlSchemaType(),
                `is`(XsIDREF))
    }

    @Test
    fun testXsENTITY() {
        assertThat(XsENTITY.typeName?.declaration, `is`(nullValue()))

        assertThat(XsENTITY.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsENTITY.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsENTITY.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsENTITY.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsENTITY.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsENTITY.typeName?.localName?.staticValue as String, `is`("ENTITY"))

        assertThat(XsENTITY.baseType, `is`(XsNCName))
        assertThat(XsENTITY.isPrimitive, `is`(false))

        assertThat(XsENTITY.toString(), `is`("xs:ENTITY"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "ENTITY").toXmlSchemaType(),
                `is`(XsENTITY))
    }

    @Test
    fun testXsBoolean() {
        assertThat(XsBoolean.typeName?.declaration, `is`(nullValue()))

        assertThat(XsBoolean.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsBoolean.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsBoolean.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsBoolean.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsBoolean.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsBoolean.typeName?.localName?.staticValue as String, `is`("boolean"))

        assertThat(XsBoolean.baseType, `is`(XsAnyAtomicType))
        assertThat(XsBoolean.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsBoolean.toString(), `is`("xs:boolean"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "boolean").toXmlSchemaType(),
                `is`(XsBoolean))
    }

    @Test
    fun testXsBase64Binary() {
        assertThat(XsBase64Binary.typeName?.declaration, `is`(nullValue()))

        assertThat(XsBase64Binary.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsBase64Binary.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsBase64Binary.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsBase64Binary.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsBase64Binary.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsBase64Binary.typeName?.localName?.staticValue as String, `is`("base64Binary"))

        assertThat(XsBase64Binary.baseType, `is`(XsAnyAtomicType))
        assertThat(XsBase64Binary.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsBase64Binary.toString(), `is`("xs:base64Binary"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "base64Binary").toXmlSchemaType(),
                `is`(XsBase64Binary))
    }

    @Test
    fun testXsHexBinary() {
        assertThat(XsHexBinary.typeName?.declaration, `is`(nullValue()))

        assertThat(XsHexBinary.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsHexBinary.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsHexBinary.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsHexBinary.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsHexBinary.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsHexBinary.typeName?.localName?.staticValue as String, `is`("hexBinary"))

        assertThat(XsHexBinary.baseType, `is`(XsAnyAtomicType))
        assertThat(XsHexBinary.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsHexBinary.toString(), `is`("xs:hexBinary"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "hexBinary").toXmlSchemaType(),
                `is`(XsHexBinary))
    }

    @Test
    fun testXsAnyURI() {
        assertThat(XsAnyURI.typeName?.declaration, `is`(nullValue()))

        assertThat(XsAnyURI.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsAnyURI.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsAnyURI.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsAnyURI.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsAnyURI.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsAnyURI.typeName?.localName?.staticValue as String, `is`("anyURI"))

        assertThat(XsAnyURI.baseType, `is`(XsAnyAtomicType))
        assertThat(XsAnyURI.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsAnyURI.toString(), `is`("xs:anyURI"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "anyURI").toXmlSchemaType(),
                `is`(XsAnyURI))
    }

    @Test
    fun testXsQName() {
        assertThat(XsQName.typeName?.declaration, `is`(nullValue()))

        assertThat(XsQName.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsQName.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsQName.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsQName.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsQName.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsQName.typeName?.localName?.staticValue as String, `is`("QName"))

        assertThat(XsQName.baseType, `is`(XsAnyAtomicType))
        assertThat(XsQName.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsQName.toString(), `is`("xs:QName"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "QName").toXmlSchemaType(),
                `is`(XsQName))
    }

    @Test
    fun testXsNOTATION() {
        assertThat(XsNOTATION.typeName?.declaration, `is`(nullValue()))

        assertThat(XsNOTATION.typeName?.prefix?.staticType, `is`(XsNCName))
        assertThat(XsNOTATION.typeName?.prefix?.staticValue as String, `is`("xs"))

        assertThat(XsNOTATION.typeName?.namespace?.staticType, `is`(XsAnyURI))
        assertThat(XsNOTATION.typeName?.namespace?.staticValue as String, `is`("http://www.w3.org/2001/XMLSchema"))

        assertThat(XsNOTATION.typeName?.localName?.staticType, `is`(XsNCName))
        assertThat(XsNOTATION.typeName?.localName?.staticValue as String, `is`("NOTATION"))

        assertThat(XsNOTATION.baseType, `is`(XsAnyAtomicType))
        assertThat(XsNOTATION.isPrimitive, `is`(true)) // XMLSchema definition

        assertThat(XsNOTATION.toString(), `is`("xs:NOTATION"))
        assertThat(createQName("http://www.w3.org/2001/XMLSchema", "NOTATION").toXmlSchemaType(),
                `is`(XsNOTATION))
    }
}
