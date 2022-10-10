package com.pluu.logger.component

internal fun interface ComponentFactory<T> {
    /**
     * Provided a [ComponentContainer], creates an instance of `T`.
     *
     * Note: It is only allowed to request declared dependencies from the container, otherwise the
     * container will throw [IllegalArgumentException].
     */
    fun create(container: ComponentContainer): T
}