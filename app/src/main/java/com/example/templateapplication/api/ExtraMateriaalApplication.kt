package com.example.templateapplication.api

import android.app.Application
import com.example.templateapplication.data.AppContainer
import com.example.templateapplication.data.DefaultAppContainer

class ExtraMateriaalApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}