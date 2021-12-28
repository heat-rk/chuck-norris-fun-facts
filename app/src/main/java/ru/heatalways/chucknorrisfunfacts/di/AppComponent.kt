package ru.heatalways.chucknorrisfunfacts.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.di.modules.*
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.RandomJokeFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.random_joke.select_category.CategorySelectionFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.search_joke.SearchJokeFragment
import ru.heatalways.chucknorrisfunfacts.presentation.screen.splash.SplashFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    AppModule::class,
    ChuckNorrisJokesModule::class,
    DatabaseModule::class,
    DispatcherModule::class,
    GsonModule::class,
    HttpClientModule::class,
    InteractorModule::class,
    RetrofitModule::class
])
interface AppComponent {
    fun inject(splashFragment: SplashFragment)
    fun inject(searchJokeFragment: SearchJokeFragment)
    fun inject(randomJokeFragment: RandomJokeFragment)
    fun inject(categorySelectionFragment: CategorySelectionFragment)
}