package com.pluu.logger

class CoreLoggerOptions(
    val isEnableCustom: Boolean,
    val isDebugMode: Boolean = false
) {
    class Builder {
        private var isEnableCustom = true
        private var isDebugMode = false

        fun isEnableCustom(isEnabled: Boolean): Builder = apply {
            this.isEnableCustom = isEnabled
        }

        fun isDebugMode(isDebugMode: Boolean): Builder = apply {
            this.isDebugMode = isDebugMode
        }

        fun build(): CoreLoggerOptions {
            return CoreLoggerOptions(
                isEnableCustom = isEnableCustom,
                isDebugMode = isDebugMode,
            )
        }
    }
}