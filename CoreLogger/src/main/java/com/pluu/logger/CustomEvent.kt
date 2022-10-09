package com.pluu.logger

import java.util.logging.Level
import java.util.logging.Logger

interface CustomEvent {
    fun event(event: String)

    class Debug : CustomEvent {
        override fun event(event: String) {
            Logger.getLogger("$TAG-CustomEvent").log(Level.INFO, "event", event)
        }
    }
}