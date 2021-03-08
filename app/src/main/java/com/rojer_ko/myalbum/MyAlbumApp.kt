package com.rojer_ko.myalbum

import android.app.Application
import com.rojer_ko.myalbum.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyAlbumApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyAlbumApp)
            modules(listOf(appModule))
        }
    }
}