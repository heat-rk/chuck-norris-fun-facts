package ru.heatalways.chucknorrisfunfacts

import android.app.Application
import android.content.Context
import ru.heatalways.chucknorrisfunfacts.di.AppComponent
import ru.heatalways.chucknorrisfunfacts.di.DaggerAppComponent
import ru.heatalways.chucknorrisfunfacts.di.modules.AppModule

open class App: Application() {
    val appComponent: AppComponent by lazy {
        initDaggerComponent()
    }

    open fun initDaggerComponent(): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
}

val Context.appComponent: AppComponent get() =
    if (this is App) appComponent
    else applicationContext.appComponent