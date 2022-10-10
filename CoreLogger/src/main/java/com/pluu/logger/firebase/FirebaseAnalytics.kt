package com.pluu.logger.firebase

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.common.Logger

class FirebaseAnalytics private constructor(
    sender: Firebase.Analytics
) : Firebase.Analytics by sender {
    companion object {
        fun getInstance(): FirebaseAnalytics {
            val app = CoreLoggerApp.getInstance()
            val instance = app.get(FirebaseAnalytics::class.java)
            requireNotNull(instance) {
                "FirebaseAnalytics component is not present."
            }
            return instance
        }

        internal fun init(sender: Firebase.Analytics): FirebaseAnalytics {
            Logger.d("Initializing Firebase Analytics")
            return FirebaseAnalytics(sender)
        }
    }
}