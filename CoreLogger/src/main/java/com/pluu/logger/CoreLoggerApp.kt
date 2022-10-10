package com.pluu.logger

import com.pluu.logger.component.Component
import com.pluu.logger.component.ComponentRegistrar
import com.pluu.logger.component.ComponentRuntime
import com.pluu.logger.component.Provider
import com.pluu.logger.custom.CustomEvent
import com.pluu.logger.custom.CustomEventRegistrar
import com.pluu.logger.firebase.Firebase
import com.pluu.logger.firebase.analytics.AnalyticsRegistrar
import com.pluu.logger.firebase.crashlytics.CrashlyticsRegistrar

class CoreLoggerApp private constructor(
    config: Config,
    val options: CoreLoggerOptions
) {
    private val componentRuntime: ComponentRuntime

    init {
        val builder = ComponentRuntime.builder()
            .addLazyComponentRegistrars(config.registers)
            .addComponent(Component.of(this, CoreLoggerApp::class.java))
            .addComponent(Component.of(options, CoreLoggerOptions::class.java))
        for (component in config.component) {
            builder.addComponent(component)
        }
        componentRuntime = builder.build()
    }

    fun <T> get(anInterface: Class<T>): T? {
        return componentRuntime.get(anInterface)
    }

    class Config private constructor(
        internal val registers: List<Provider<ComponentRegistrar>>,
        internal val component: List<Component<*>>,
    ) {
        class Builder {
            private val registers = mutableListOf<Provider<ComponentRegistrar>>()
            private val component = mutableListOf<Component<*>>()

            fun register(sender: Firebase.Crashlytics) = apply {
                registers.add { CrashlyticsRegistrar() }
                addComponent(sender)
            }

            fun register(sender: Firebase.Analytics) = apply {
                registers.add { AnalyticsRegistrar() }
                addComponent(sender)
            }

            fun register(sender: CustomEvent) = apply {
                registers.add { CustomEventRegistrar() }
                addComponent(sender)
            }

            private inline fun <reified T : Any> addComponent(sender: T) {
                component.add(Component.of(sender, T::class.java))
            }

            fun build(): Config = Config(
                registers.toList(),
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
