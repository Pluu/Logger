package com.pluu.logger.component

internal class ComponentRuntime {
    private val instanceMap = HashMap<Class<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    fun <T> get(anInterface: Class<T>): T? {
        return instanceMap[anInterface] as? T
    }

    fun <T : Any> set(anInterface: Class<out T>, instance: T) {
        instanceMap[anInterface] = instance
    }
}