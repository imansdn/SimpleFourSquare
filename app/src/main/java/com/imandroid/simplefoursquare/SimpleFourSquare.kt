package com.imandroid.simplefoursquare

import android.app.Application
import timber.log.Timber

class SimpleFourSquare: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())


    }
}