package com.imandroid.simplefoursquare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import timber.log.Timber


class SimpleFourSquare: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        mContext = applicationContext
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        fun getAppContext(): Context? {
            return mContext
        }
    }

}