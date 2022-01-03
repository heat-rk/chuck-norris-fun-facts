package ru.heatalways.chucknorrisfunfacts

import ru.heatalways.chucknorrisfunfacts.di.AppComponent
import ru.heatalways.chucknorrisfunfacts.di.DaggerTestAppComponent
import ru.heatalways.chucknorrisfunfacts.di.modules.AppModule

class TestApp: App() {
    override fun initDaggerComponent(): AppComponent =
        DaggerTestAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
}