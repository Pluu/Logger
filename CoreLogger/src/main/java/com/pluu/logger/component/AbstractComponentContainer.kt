package com.pluu.logger.component

internal abstract class AbstractComponentContainer : ComponentContainer {
    override fun <T> get(anInterface: Class<T>): T? {
        return getProvider(anInterface)?.get()
    }
}