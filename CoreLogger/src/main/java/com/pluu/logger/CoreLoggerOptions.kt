package com.pluu.logger

class CoreLoggerOptions(
    val isEnableEvent: Boolean = true,
    val isDebugMode: Boolean = false
) {
    class Builder {
        private var isEnableEvent = true
        private var isDebugMode = false

        fun isEnableEvent(isEnabled: Boolean): Builder = apply {
            this.isEnableEvent = isEnabled
        }

        fun isDebugMode(isDebugMode: Boolean): Builder = apply {
            this.isDebugMode = isDebugMode
        }

        fun build(): CoreLoggerOptions {
            return CoreLoggerOptions(
                isEnableEvent = isEnableEvent,
                isDebugMode = isDebugMode,
            )
        }
    }
}