package com.microlabs.trallet.base

import android.app.Application
import com.microlabs.trallet.BuildConfig
import timber.log.Timber

/**
 * Base App Class for Realm Configuration
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
//        Fabric.with(this, Crashlytics())
    }
}
