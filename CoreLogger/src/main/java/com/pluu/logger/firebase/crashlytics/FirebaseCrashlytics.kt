package com.pluu.logger.firebase.crashlytics

import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.common.Logger
import com.pluu.logger.firebase.Firebase

internal class FirebaseCrashlytics private constructor(
    sender: Firebase.Crashlytics
) : Firebase.Crashlytics by sender {
    companion object {
        fun getInstance(): FirebaseCrashlytics {
            val app = CoreLoggerApp.getInstance()
            val instance = app.get(FirebaseCrashlytics::class.java)
            requireNotNull(instance) {
                "FirebaseCrashlytics component is not present."
            }
            return instance
        }

        internal fun init(sender: Firebase.Crashlytics): FirebaseCrashlytics {
            Logger.d("Initializing Firebase Crashlytics")
            return FirebaseCrashlytics(sender)
        }
    }
}