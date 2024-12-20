package com.example.pruebatecnicatest

import android.app.Application
import com.example.pruebatecnicatest.di.AppModule
import com.example.pruebatecnicatest.di.LocalModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@MainApplication)
            modules(AppModule, LocalModule)
        }
    }
}