package com.pluu.sample.logger

import android.app.Application
import com.pluu.sample.corelogger.CoreLogger
import com.pluu.sample.corelogger.CustomEvent
import com.pluu.sample.corelogger.Firebase

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