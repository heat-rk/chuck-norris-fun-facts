package ru.heatalways.chucknorrisfunfacts.di.components

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Component
import ru.heatalways.chucknorrisfunfacts.di.modules.ChuckNorrisJokesModule
import ru.heatalways.chucknorrisfunfacts.di.modules.GlideModule
import ru.heatalways.chucknorrisfunfacts.di.modules.NavigationModule
import ru.heatalways.chucknorrisfunfacts.domain.services.chuck_norris_jokes.ChuckNorrisJokesService
import ru.heatalways.chucknorrisfunfacts.domain.services.glide.GlideService
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ChuckNorrisJokesModule::class,
    GlideModule::class,
    NavigationModule::class
])
interface AppComponent {
    fun getChuckNorrisJokesService(): ChuckNorrisJokesService
    fun getGlideService(): GlideService
    fun getCicerone(): Cicerone<Router>
    fun getRouter(): Router
}