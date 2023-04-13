package com.example.searchpoison.app

import android.app.Application
import com.example.searchpoison.di.applicationModule
import com.example.searchpoison.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(applicationModule, viewModelModule)
            )
        }
    }
}