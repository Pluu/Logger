package com.pluu.logger.custom

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.common.Logger

internal class CustomEventSender(
    sender: CustomEvent
) : CustomEvent by sender {
    companion object {
        fun getInstance(): CustomEventSender {
            val app = CoreLoggerApp.getInstance()
            val instance = app.get(CustomEventSender::class.java)
            requireNotNull(instance) {
                "CustomEventLogger component is not present."
            }
            return instance
        }

        internal fun init(sender: CustomEvent): CustomEventSender {
            Logger.d("Initializing Custom event")
            return CustomEventSender(sender)
        }
    }
}