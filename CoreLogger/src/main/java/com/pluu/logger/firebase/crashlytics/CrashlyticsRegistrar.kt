package com.pluu.logger.firebase.crashlytics

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.component.Component
import com.pluu.logger.component.ComponentContainer
import com.pluu.logger.component.ComponentRegistrar
import com.pluu.logger.component.Dependency
import com.pluu.logger.firebase.Firebase

internal class CrashlyticsRegistrar : ComponentRegistrar {
    override fun getComponent(): Component<*> {
        return Component.builder(FirebaseCrashlytics::class.java)
            .name("CrashlyticsRegistrar")
            .add(Dependency.required(CoreLoggerApp::class.java))
            .add(Dependency.required(Firebase.Crashlytics::class.java))
            .factory(this::buildCrashlytics)
            .build()
    }

    private fun buildCrashlytics(container: ComponentContainer): FirebaseCrashlytics {
        val app = container.get(CoreLoggerApp::class.java)
        requireNotNull(app) {
            "CoreLoggerApp component is not present."
        }
        val sender = app.get(Firebase.Crashlytics::class.java)
        requireNotNull(sender) {
            "Event sender component is not present."
        }
        return FirebaseCrashlytics.init(sender)
    }
}