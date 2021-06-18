package ru.heatalways.chucknorrisfunfacts.di.components

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Component
import ru.heatalways.chucknorrisfunfacts.di.modules.ChuckNorrisJokesModule
import ru.heatalways.chucknorrisfunfacts.di.modules.GlideModule
import ru.heatalways.chucknorrisfunfacts.di.modules.NavigationModule
import ru.heatalways.chucknorrisfunfacts.domain.managers.chuck_norris_jokes.ChuckNorrisJokesManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.ImageLoaderManager
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ChuckNorrisJokesModule::class,
    GlideModule::class,
    NavigationModule::class
])
interface AppComponent {
    fun getChuckNorrisJokesService(): ChuckNorrisJokesManager
    fun getGlideService(): ImageLoaderManager
    fun getCicerone(): Cicerone<Router>
    fun getRouter(): Router
}