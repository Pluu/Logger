package com.pluu.logger

import com.pluu.logger.component.Component
import com.pluu.logger.component.ComponentRuntime
import com.pluu.logger.custom.CustomEvent
import com.pluu.logger.custom.CustomEventSender
import com.pluu.logger.firebase.Firebase
import com.pluu.logger.firebase.FirebaseAnalytics
import com.pluu.logger.firebase.FirebaseCrashlytics

@JvmSynthetic
internal val TAG = CoreLoggerApp::class.simpleName

class CoreLoggerApp private constructor(
    config: Config,
    val options: CoreLoggerOptions
) {
    private val componentRuntime = ComponentRuntime()

    init {
        for (component in config.component) {
            componentRuntime.set(component.providedInterfaces, component.value)
        }
    }

    fun <T> get(anInterface: Class<T>): T? {
        return componentRuntime.get(anInterface)
    }

    class Config private constructor(
        internal val component: List<Component<*>>,
    ) {
        class Builder {
            private val component = mutableListOf<Component<*>>()

            fun register(sender: Firebase.Crashlytics) = apply {
                val wrapper = FirebaseCrashlytics.init(sender)
                component.add(Component.of(wrapper, FirebaseCrashlytics::class.java))
            }

            fun register(sender: Firebase.Analytics) = apply {
                val wrapper = FirebaseAnalytics.init(sender)
                component.add(Component.of(wrapper, FirebaseAnalytics::class.java))
            }

            fun register(sender: CustomEvent) = apply {
                val wrapper = CustomEventSender.init(sender)
                component.add(Component.of(wrapper, CustomEventSender::class.java))
            }

            fun build(): Config = Config(
                component.toList()
            )
        }
    }

    companion object {
        @Volatile
        private var mInstance: CoreLoggerApp? = null

        fun getInstance(): CoreLoggerApp {
            return requireNotNull(mInstance) {
                "Default CoreLoggerApp is not initialized. " +
                        "Make sure to call CoreLoggerApp.initializeApp() first."
            }
        }

        fun initializeApp(config: Config): CoreLoggerApp {
            return initializeApp(config, CoreLoggerOptions.Builder().build())
        }

        fun initializeApp(config: Config, options: CoreLoggerOptions): CoreLoggerApp {
            return mInstance ?: synchronized(this) {
                mInstance ?: CoreLoggerApp(config, options).also { mInstance = it }
            }
        }
    }
}
