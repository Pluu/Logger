package com.pluu.logger

import com.pluu.logger.custom.CustomEvent
import com.pluu.logger.custom.CustomEventSender
import com.pluu.logger.firebase.Firebase
import com.pluu.logger.firebase.FirebaseAnalytics
import com.pluu.logger.firebase.FirebaseCrashlytics

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