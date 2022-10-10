package com.pluu.logger.component

internal class Component<T : Any> private constructor(
    val providedInterfaces: Class<T>,
    val value: T
) {
    val name: String = providedInterfaces.simpleName

    companion object {
        fun <T : Any> of(value: T, anInterface: Class<T>): Component<T> {
            return Component(anInterface, value)
        }
    }
}