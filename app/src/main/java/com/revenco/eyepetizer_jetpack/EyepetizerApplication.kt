package com.revenco.eyepetizer_jetpack

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates


class EyepetizerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        var context: Context by Delegates.notNull()
    }

}