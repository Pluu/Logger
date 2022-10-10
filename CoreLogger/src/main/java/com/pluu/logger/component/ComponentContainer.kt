package com.pluu.logger.component

internal interface ComponentContainer {
    fun <T> get(anInterface: Class<T>): T?

    fun <T> getProvider(anInterface: Class<T>): Provider<T>?
}