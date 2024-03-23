package org.example.project

import android.app.Application
import android.content.Context

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        APPLICATION_CONTEXT = this
    }

    companion object {
        lateinit var APPLICATION_CONTEXT: Context
            private set
    }
}