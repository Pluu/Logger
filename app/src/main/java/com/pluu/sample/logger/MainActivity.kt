package com.pluu.sample.logger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluu.logger.CoreLogger
import com.pluu.logger.analytics
import com.pluu.logger.crashlytics
import com.pluu.logger.customEvent
import com.pluu.logger.firebase
import com.pluu.sample.logger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrashlytics.setOnClickListener {
            CoreLogger.firebase
                .crashlytics
                .sendCrashlytics(IllegalStateException("Sample Error"))
        }

        binding.btnAnalytics.setOnClickListener {
            CoreLogger.firebase
                .analytics
                .sendEvent("Sample Key", mapOf("A" to 1))
        }

        binding.btnCustomEvent.setOnClickListener {
            CoreLogger.customEvent
                .event("Sample Event")
        }
    }
}