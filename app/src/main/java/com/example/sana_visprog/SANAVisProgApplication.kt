package com.example.sana_visprog

import android.app.Application
import com.example.sana_visprog.container.AppContainer

class SANAVisProgApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}