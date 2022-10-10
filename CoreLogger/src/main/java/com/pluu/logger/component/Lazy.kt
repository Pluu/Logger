package com.pluu.logger.component

internal class Lazy<T> : Provider<T> {
    @Volatile
    private var instance = UNINITIALIZED

    @Volatile
    private var provider: Provider<T>? = null

    /** Creates a [Lazy] with a fully initialized value. */
    internal constructor(instance: T) {
        this.instance = instance as Any
    }

    constructor(provider: Provider<T>?) {
        this.provider = provider
    }

    /** Returns the initialized value. */
    @Suppress("UNCHECKED_CAST")
    override fun get(): T {
        var result = instance
        if (result === UNINITIALIZED) {
            synchronized(this) {
                result = instance
                if (result === UNINITIALIZED) {
                    result = provider?.get() as Any
                    instance = result
                    // Null out the reference to the provider.
                    // We are never going to need it again,
                    // so we can make it eligible for GC.
                    provider = null
                }
            }
        }
        return result as T
    }

    val isInitialized: Boolean
        get() = instance !== UNINITIALIZED

    companion object {
        private val UNINITIALIZED = Any()
    }
}