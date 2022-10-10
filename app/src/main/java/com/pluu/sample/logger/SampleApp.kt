package com.pluu.sample.logger

import android.app.Application
import android.util.Log
import com.pluu.logger.CoreLoggerApp
import com.pluu.logger.CoreLoggerOptions
import com.pluu.logger.custom.CustomEvent
import com.pluu.logger.firebase.Firebase

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()

        CoreLoggerApp.initializeApp(
            CoreLoggerApp.Config.Builder()
                .register(
                    object : Firebase.Analytics {
                        override fun sendEvent(name: String, params: Map<String, Any>) {
                            Log.d("Firebase.Analytics", "name=${name}, param=${params}")
                        }
                    }
                )
                .register(
                    object : Firebase.Crashlytics {
                        override fun sendCrashlytics(throwable: Throwable) {
                            Log.e("Firebase.Crashlytics", "Sample", throwable)
                        }
                    }
                )
                .register(
                    object : CustomEvent {
                        override fun event(event: String) {
                            Log.d("CustomEvent", "event=${event}")
                        }
                    }
                )
                .build(),
            CoreLoggerOptions()
        )
    }
}