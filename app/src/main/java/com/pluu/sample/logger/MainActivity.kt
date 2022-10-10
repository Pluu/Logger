package com.pluu.sample.logger

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pluu.logger.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btnCrashlytics).setOnClickListener {
            CoreLogger.firebase
                .crashlytics
                .sendCrashlytics(IllegalStateException("Sample Error"))
        }

        findViewById<View>(R.id.btnAnalytics).setOnClickListener {
            CoreLogger.firebase
                .analytics
                .sendEvent("Sample Key", mapOf("A" to 1))
        }

        findViewById<View>(R.id.btnCustomEvent).setOnClickListener {
            CoreLogger.customEvent
                .event("Sample Event")
        }
    }
}