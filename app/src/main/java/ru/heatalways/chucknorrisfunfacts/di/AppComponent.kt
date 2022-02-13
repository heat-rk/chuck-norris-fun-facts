package ru.heatalways.chucknorrisfunfacts.di

import android.app.Application
import dagger.Component
import dagger.internal.Preconditions
import ru.heatalways.chucknorrisfunfacts.di.modules.AppModule
import ru.heatalways.chucknorrisfunfacts.di.modules.DependenciesModule
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.splash.SplashFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DependenciesModule::class
])
interface AppComponent {
    fun inject(application: Application)
    fun inject(splashFragment: SplashFragment)
    fun inject(mainFragment: MainFragment)

    companion object {
        @Volatile
        private var instance: AppComponent? = null

        fun get(): AppComponent {
            return Preconditions.checkNotNull(instance,
                "AppComponent is not initialized yet. Call init first.")!!
        }

        fun init(component: AppComponent) {
            require(instance == null) { "AppComponent is already initialized." }
            instance = component
        }
    }
}