package com.pluu.logger

import java.util.logging.Level
import java.util.logging.Logger

interface Firebase {
    val crashlytics: Crashlytics

    val analytics: Analytics

    interface Crashlytics {
        fun sendCrashlytics(throwable: Throwable)

        class Debug : Crashlytics {
            override fun sendCrashlytics(throwable: Throwable) {
                Logger.getLogger("$TAG-Crashlytics").log(Level.SEVERE, "sendCrashlytics", throwable)
            }
        }
    }

    interface Analytics {
        fun sendEvent(name: String, params: Map<String, Any>)

        class Debug : Analytics {
            override fun sendEvent(name: String, params: Map<String, Any>) {
                Logger.getLogger("$TAG-Analytics").log(Level.INFO, "sendEvent", params)
            }
        }
    }
}