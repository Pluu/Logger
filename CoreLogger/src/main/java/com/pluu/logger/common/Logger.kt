package com.pluu.logger.common

import java.util.logging.Level
import java.util.logging.Logger

internal class Logger {
    companion object {
        fun d(message: String) {
            Logger.getLogger("Logger").log(Level.INFO, message)
        }

        fun w(message: String, throwable: Throwable) {
            Logger.getLogger("Logger").log(Level.INFO, message, throwable)
        }
    }
}