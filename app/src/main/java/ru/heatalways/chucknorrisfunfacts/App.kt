package ru.heatalways.chucknorrisfunfacts

import android.app.Application
import com.github.terrakok.cicerone.Navigator
import ru.heatalways.chucknorrisfunfacts.di.components.AppComponent
import ru.heatalways.chucknorrisfunfacts.di.components.DaggerAppComponent

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initDagger()
    }

    private fun initDagger() {
        mAppComponent = DaggerAppComponent.create()
    }

    fun setNavigator(navigator: Navigator) {
        mAppComponent.getCicerone().getNavigatorHolder().setNavigator(navigator)
    }

    fun removeNavigator() {
        mAppComponent.getCicerone().getNavigatorHolder().removeNavigator()
    }

    companion object {
        private lateinit var mInstance: App
        private lateinit var mAppComponent: AppComponent

        val appComponent get() = mAppComponent
        val instance get() = mInstance
    }
}