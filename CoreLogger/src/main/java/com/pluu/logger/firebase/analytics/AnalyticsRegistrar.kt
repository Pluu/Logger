package com.pluu.logger.firebase.analytics

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.component.Component
import com.pluu.logger.component.ComponentContainer
import com.pluu.logger.component.ComponentRegistrar
import com.pluu.logger.component.Dependency
import com.pluu.logger.firebase.Firebase

internal class AnalyticsRegistrar : ComponentRegistrar {
    override fun getComponent(): Component<*> {
        return Component.builder(FirebaseAnalytics::class.java)
            .name("AnalyticsRegistrar")
            .add(Dependency.required(CoreLoggerApp::class.java))
            .add(Dependency.required(Firebase.Analytics::class.java))
            .factory(this::buildCrashlytics)
            .build()
    }

    private fun buildCrashlytics(container: ComponentContainer): FirebaseAnalytics {
        val app = container.get(CoreLoggerApp::class.java)
        requireNotNull(app) {
            "CoreLoggerApp component is not present."
        }
        val sender = app.get(Firebase.Analytics::class.java)
        requireNotNull(sender) {
            "Event sender component is not present."
        }
        return FirebaseAnalytics.init(sender)
    }
}