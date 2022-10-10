package com.pluu.logger.component

/** Provides a fully constructed instance of T.  */
internal fun interface Provider<T> {
    fun get(): T
}