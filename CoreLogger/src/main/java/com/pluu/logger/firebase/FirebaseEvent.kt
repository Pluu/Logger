package com.pluu.logger.firebase

interface Firebase {
    interface Crashlytics {
        fun sendCrashlytics(throwable: Throwable)
    }

    interface Analytics {
        fun sendEvent(name: String, params: Map<String, Any>)
    }
}