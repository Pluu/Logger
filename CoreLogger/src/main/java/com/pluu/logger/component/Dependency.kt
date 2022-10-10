package com.pluu.logger.component

internal class Dependency private constructor(
    private val anInterface: Class<*>
) {
    fun getInterface(): Class<*> {
        return anInterface
    }

    override fun toString(): String {
        val sb = StringBuilder("Dependency{anInterface=")
            .append(anInterface)
            .append("}")
        return sb.toString()
    }

    companion object {
        fun required(anInterface: Class<*>): Dependency {
            return Dependency(anInterface)
        }
    }
}