package com.pluu.sample.logger

import android.app.Application
import com.pluu.logger.CoreLogger
import com.pluu.logger.CustomEvent
import com.pluu.logger.Firebase

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        CoreLogger.config(
            CoreLogger.Config()
                .register(Firebase.Crashlytics.Debug())
                .register(Firebase.Analytics.Debug())
                .register(CustomEvent.Debug())
        )
    }
}