package ru.heatalways.chucknorrisfunfacts

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import ru.heatalways.chucknorrisfunfacts.di.components.AppComponent
import ru.heatalways.chucknorrisfunfacts.di.components.DaggerAppComponent
import ru.heatalways.chucknorrisfunfacts.domain.services.glide.GlideService

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