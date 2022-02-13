package ru.heatalways.chucknorrisfunfacts.di

import dagger.Component
import ru.heatalways.chucknorrisfunfacts.core.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.core.di.modules.*
import ru.heatalways.chucknorrisfunfacts.di.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ChuckNorrisJokesModuleFake::class,
    DatabaseModuleFake::class,
    DispatcherModule::class,
    GsonModule::class,
    HttpClientModule::class,
    RetrofitModule::class
])
interface TestAppComponent: AppComponent {
    val database: AppDatabase
}