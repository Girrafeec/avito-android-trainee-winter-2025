package com.girrafeec.avito_deezer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AvitoDeezerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.IS_LOGGING_ENABLED) Timber.plant(Timber.DebugTree())
    }
}
