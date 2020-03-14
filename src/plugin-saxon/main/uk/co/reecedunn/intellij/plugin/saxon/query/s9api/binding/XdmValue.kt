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
package uk.co.reecedunn.intellij.plugin.saxon.query.s9api.binding

import uk.co.reecedunn.intellij.plugin.core.reflection.loadClassOrNull
import uk.co.reecedunn.intellij.plugin.saxon.query.s9api.binding.type.Type

open class XdmValue(val saxonObject: Any, private val `class`: Class<*>) {
    fun iterator(): XdmSequenceIterator {
        val xdmSequenceIteratorClass = `class`.classLoader.loadClass("net.sf.saxon.s9api.XdmSequenceIterator")
        return XdmSequenceIterator(`class`.getMethod("iterator").invoke(saxonObject), xdmSequenceIteratorClass)
    }

    private fun getUnderlyingValue(): Any {
        return `class`.getMethod("getUnderlyingValue").invoke(saxonObject)
    }

    fun getItemType(): Any {
        val value = getUnderlyingValue()
        return Type.getItemType(value, `class`.classLoader)
    }

    override fun toString(): String {
        return saxonObject.toString()
    }

    override fun hashCode(): Int {
        return saxonObject.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is XdmItem) return false
        return saxonObject == other.saxonObject
    }

    companion object {
        fun newInstance(value: Any?, type: String, processor: Processor): XdmValue {
            return when (type) {
                "empty-sequence()" -> XdmEmptySequence.getInstance(processor.classLoader)
                else -> XdmItem.newInstance(value, type, processor)
            }
        }

        fun wrap(sequence: Any, classLoader: ClassLoader): XdmValue {
            val xdmValueClass = classLoader.loadClass("net.sf.saxon.s9api.XdmValue")
            val sequenceClass = classLoader.loadClassOrNull("net.sf.saxon.om.Sequence") // Saxon >= 9.5
            return if (sequenceClass != null) { // Saxon >= 9.5
                XdmValue(xdmValueClass.getMethod("wrap", sequenceClass).invoke(null, sequence), xdmValueClass)
            } else { // Saxon <= 9.4
                val valueRepresentationClass = classLoader.loadClass("net.sf.saxon.om.ValueRepresentation")
                val wrap = xdmValueClass.getDeclaredMethod("wrap", valueRepresentationClass)
                wrap.isAccessible = true // XdmValue.wrap is protected.
                XdmValue(wrap.invoke(null, sequence), xdmValueClass)
            }
        }
    }
}
