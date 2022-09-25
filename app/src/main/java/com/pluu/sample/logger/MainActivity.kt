package com.pluu.sample.logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluu.sample.corelogger.CoreLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoreLogger.firebase
            .crashlytics
            .sendCrashlytics(IllegalStateException("Sample Error"))

        CoreLogger.firebase
            .analytics
            .sendEvent("Sample Key", mapOf("A" to 1))

        CoreLogger.customEvent
            .event("Sample Event")
    }
}