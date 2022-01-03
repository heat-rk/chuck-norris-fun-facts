package ru.heatalways.chucknorrisfunfacts.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModule::class,
    AppModule::class,
    ChuckNorrisJokesModule::class,
    DatabaseModuleFake::class,
    DispatcherModule::class,
    GsonModule::class,
    HttpClientModule::class,
    InteractorModule::class,
    RetrofitModule::class
])
interface TestAppComponent: AppComponent {
    val database: AppDatabase
}