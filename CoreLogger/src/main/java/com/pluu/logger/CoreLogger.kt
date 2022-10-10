package com.pluu.logger

import com.pluu.logger.custom.CustomEvent
import com.pluu.logger.custom.CustomEventSender
import com.pluu.logger.firebase.Firebase
import com.pluu.logger.firebase.analytics.FirebaseAnalytics
import com.pluu.logger.firebase.crashlytics.FirebaseCrashlytics

object CoreLogger

object FirebaseLogger

val CoreLogger.customEvent: CustomEvent
    get() = CustomEventSender.getInstance()

val CoreLogger.firebase: FirebaseLogger
    get() = FirebaseLogger

val FirebaseLogger.analytics: Firebase.Analytics
    get() = FirebaseAnalytics.getInstance()

val FirebaseLogger.crashlytics: Firebase.Crashlytics
    get() = FirebaseCrashlytics.getInstance()