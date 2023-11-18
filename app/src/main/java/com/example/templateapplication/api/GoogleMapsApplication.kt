package com.example.templateapplication.api

import android.app.Application
import com.example.templateapplication.data.DefaultGoogleMapsAppContainerAppContainer
import com.example.templateapplication.data.GoogleMapsAppContainer

class GoogleMapsApplication: Application() {
    lateinit var container: GoogleMapsAppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultGoogleMapsAppContainerAppContainer()
    }
}