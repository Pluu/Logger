package com.pluu.logger.custom

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.component.Component
import com.pluu.logger.component.ComponentContainer
import com.pluu.logger.component.ComponentRegistrar
import com.pluu.logger.component.Dependency

internal class CustomEventRegistrar : ComponentRegistrar {
    override fun getComponent(): Component<*> {
        return Component.builder(CustomEventSender::class.java)
            .name("CustomEventSender")
            .add(Dependency.required(CoreLoggerApp::class.java))
            .add(Dependency.required(CustomEvent::class.java))
            .factory(this::buildCrashlytics)
            .build()
    }

    private fun buildCrashlytics(container: ComponentContainer): CustomEventSender {
        val app = container.get(CoreLoggerApp::class.java)
        requireNotNull(app) {
            "CoreLoggerApp component is not present."
        }
        val sender = app.get(CustomEvent::class.java)
        requireNotNull(sender) {
            "Event sender component is not present."
        }
        return CustomEventSender.init(sender)
    }
}