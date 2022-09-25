package com.pluu.sample.corelogger

import java.util.logging.Level
import java.util.logging.Logger

private val TAG = CoreLogger::class.simpleName

object CoreLogger {
    @JvmStatic
    val firebase: Firebase
        get() = CoreLoggerGlobal.firebase

    @JvmStatic
    val customEvent: CustomEvent
        get() = CoreLoggerGlobal.customEvent

    class Config {
        var crashlytics: Firebase.Crashlytics? = null
        var analytics: Firebase.Analytics? = null
        var event: CustomEvent? = null

        fun register(crashlytics: Firebase.Crashlytics) = apply {
            this.crashlytics = crashlytics
        }

        fun register(analytics: Firebase.Analytics) = apply {
            this.analytics = analytics
        }

        fun register(event: CustomEvent) = apply {
            this.event = event
        }
    }

    fun config(config: Config) {
        config.analytics?.let {
            CoreLoggerGlobal.register(it)
        }
        config.crashlytics?.let {
            CoreLoggerGlobal.register(it)
        }
        config.event?.let {
            CoreLoggerGlobal.register(it)
        }
    }
}

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

interface CustomEvent {
    fun event(event: String)

    class Debug : CustomEvent {
        override fun event(event: String) {
            Logger.getLogger("$TAG-CustomEvent").log(Level.INFO, "event", event)
        }
    }
}

///////////////////////////////////////////////////////////////////////////
//
///////////////////////////////////////////////////////////////////////////

private object CoreLoggerGlobal {
    private var _crashlytics: Firebase.Crashlytics? = null
    private var _analytics: Firebase.Analytics? = null
    private var _customEvent: CustomEvent? = null

    val firebase: Firebase by lazy {
        FirebaseLoggerWrapper(
            _crashlytics = { _crashlytics },
            _analytics = { _analytics }
        )
    }

    val customEvent: CustomEvent by lazy {
        requireNotNull(_customEvent) {
            "Not init CustomEvent. Use, CoreLogger.config(...)"
        }
    }

    fun register(crashlytics: Firebase.Crashlytics) {
        this._crashlytics = crashlytics
    }

    fun register(analytics: Firebase.Analytics) {
        this._analytics = analytics
    }

    fun register(event: CustomEvent) {
        this._customEvent = event
    }
}

private class FirebaseLoggerWrapper(
    private val _crashlytics: () -> Firebase.Crashlytics?,
    private val _analytics: () -> Firebase.Analytics?,
) : Firebase {
    override val crashlytics: Firebase.Crashlytics
        get() = requireNotNull(_crashlytics()) {
            "Not init Firebase.Crashlytics. Use, CoreLogger.config(...)"
        }

    override val analytics: Firebase.Analytics
        get() = requireNotNull(_analytics()) {
            "Not init Firebase.Analytics. Use, CoreLogger.config(...)"
        }
}