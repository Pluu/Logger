## Sample #1

Source : https://github.com/Pluu/LoggerSample/tree/main

```kotlin
// Firebase Crashlytics
CoreLogger.firebase
  .crashlytics
  .sendCrashlytics(/** Exception */)

// Firebase Crashlytics
CoreLogger.firebase
  .analytics
  .sendEvent(/** Key */, /** Value */)

// Custom Event
CoreLogger.customEvent
  .event(/** Custom Event log */)
```

Sample Result

<img src="arts/sample.png" />

### Initialize

```kotlin
class SampleApp : Application() {
  override fun onCreate() {
    ...
    CoreLogger.config(
      CoreLogger.Config()
        .register(/** Optional, Init Firebase.Crashlytics */)
        .register(/** Optional, Init Firebase.Analytics */)
        .register(/** Optional, CustomEvent */)
    )      
  }
}
```

실제 사용하기 위해서는 Config가 필수 요소

## Sample #2

Source : https://github.com/Pluu/LoggerSample/tree/firebase-simple

```kotlin
class SampleApp : Application() {
  override fun onCreate() {
    ...
    CoreLoggerApp.initializeApp(
      CoreLoggerApp.Config.Builder()
        .register(/** Optional, Init Firebase.Crashlytics */)
        .register(/** Optional, Init Firebase.Analytics */)
        .register(/** Optional, Init CustomEvent */)
        .build()
    )
  }
}
```

## Sample #3 ~ Lazy Style

Source : https://github.com/Pluu/LoggerSample/tree/firebase-style

```kotlin
class SampleApp : Application() {
  override fun onCreate() {
    ...
    CoreLoggerApp.initializeApp(
      CoreLoggerApp.Config.Builder()
        .register(/** Optional, Init Firebase.Crashlytics */)
        .register(/** Optional, Init Firebase.Analytics */)
        .register(/** Optional, Init CustomEvent */)
        .build()
    )
  }
}
```

