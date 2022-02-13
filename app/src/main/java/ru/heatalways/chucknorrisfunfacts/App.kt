package ru.heatalways.chucknorrisfunfacts

import android.app.Application
import ru.heatalways.chucknorrisfunfacts.di.AppComponent
import ru.heatalways.chucknorrisfunfacts.di.DaggerAppComponent
import ru.heatalways.chucknorrisfunfacts.di.modules.AppModule

open class App: Application() {
    override fun onCreate() {
        AppComponent.init(
            DaggerAppComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
        )

        super.onCreate()
    }
}