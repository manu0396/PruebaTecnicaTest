package com.example.pruebatecnicatest

import android.app.Application
import android.content.Context
import com.example.pruebatecnicatest.di.AppComponent
import com.example.pruebatecnicatest.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import com.google.firebase.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        appComponent = DaggerAppComponent.builder().application(this).build()

    }
}