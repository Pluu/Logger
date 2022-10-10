package com.pluu.logger.component

internal fun interface ComponentRegistrar {
    /** Returns a list of components provided by this registrar. */
    fun getComponent(): Component<*>
}