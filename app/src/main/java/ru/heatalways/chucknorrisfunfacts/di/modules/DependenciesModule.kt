package ru.heatalways.chucknorrisfunfacts.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.core.di.CoreApi
import ru.heatalways.chucknorrisfunfacts.core.di.CoreComponentHolder
import ru.heatalways.chucknorrisfunfacts.core.di.CoreDependencies
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.ChuckNorrisJokesCoreApi
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.ChuckNorrisJokesCoreComponentHolder
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.di.ChuckNorrisJokesCoreDependencies
import ru.heatalways.chucknorrisfunfacts.core_settings.di.SettingsCoreApi
import ru.heatalways.chucknorrisfunfacts.core_settings.di.SettingsCoreComponentHolder
import ru.heatalways.chucknorrisfunfacts.core_settings.di.SettingsCoreDependencies
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureApi
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.random_joke.di.RandomJokeFeatureDependencies
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureApi
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.search_joke.di.SearchJokeFeatureDependencies
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureApi
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureComponentHolder
import ru.heatalways.chucknorrisfunfacts.settings.di.SettingsFeatureDependencies
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DependenciesModule {
    @Provides
    @Singleton
    fun provideSettingsInteractor(settingsCoreApi: SettingsCoreApi) =
        settingsCoreApi.getSettingsInteractor()

    @Provides
    @Singleton
    fun provideChuckNorrisJokesInteractor(chuckNorrisJokesCoreApi: ChuckNorrisJokesCoreApi) =
        chuckNorrisJokesCoreApi.getChuckNorrisJokesInteractor()

    @Provides
    @Singleton
    fun provideDatabaseCoreDependencies(applicationContext: Context) =
        object: CoreDependencies {
            override fun getApplicationContext() =
                applicationContext
        }

    @Provides
    @Singleton
    fun provideChuckNorrisJokesCoreDependencies(coreApi: CoreApi) =
        object: ChuckNorrisJokesCoreDependencies {
            override fun getAppDatabase() =
                coreApi.getAppDatabase()

            override fun getRetrofitBuilder() =
                coreApi.getRetrofitBuilder()

            override fun getIoDispatcher() =
                coreApi.getIoDispatcher()
        }

    @Provides
    @Singleton
    fun provideSettingsCoreDependencies(applicationContext: Context) =
        object: SettingsCoreDependencies {
            override fun getApplicationContext() =
                applicationContext
        }

    @Provides
    @Singleton
    fun provideSettingsFeatureDependencies(
        settingsCoreApi: SettingsCoreApi
    ) =
        object: SettingsFeatureDependencies {
            override fun getSettingsInteractor() =
                settingsCoreApi.getSettingsInteractor()
        }

    @Provides
    @Singleton
    fun provideRandomJokeFeatureDependencies(
        chuckNorrisJokesCoreApi: ChuckNorrisJokesCoreApi
    ) =
        object: RandomJokeFeatureDependencies {
            override fun getChuckNorrisJokesInteractor() =
                chuckNorrisJokesCoreApi.getChuckNorrisJokesInteractor()
        }

    @Provides
    @Singleton
    fun provideSearchJokeFeatureDependencies(
        chuckNorrisJokesCoreApi: ChuckNorrisJokesCoreApi
    ) =
        object: SearchJokeFeatureDependencies {
            override fun getChuckNorrisJokesInteractor() =
                chuckNorrisJokesCoreApi.getChuckNorrisJokesInteractor()
        }




    @Provides
    fun provideCore(dependencies: CoreDependencies): CoreApi {
        CoreComponentHolder.init(dependencies)
        return CoreComponentHolder.get()
    }

    @Provides
    fun provideChuckNorrisJokesCore(dependencies: ChuckNorrisJokesCoreDependencies): ChuckNorrisJokesCoreApi {
        ChuckNorrisJokesCoreComponentHolder.init(dependencies)
        return ChuckNorrisJokesCoreComponentHolder.get()
    }

    @Provides
    fun provideSettingsCore(dependencies: SettingsCoreDependencies): SettingsCoreApi {
        SettingsCoreComponentHolder.init(dependencies)
        return SettingsCoreComponentHolder.get()
    }

    @Provides
    fun provideSettingsFeature(dependencies: SettingsFeatureDependencies): SettingsFeatureApi {
        SettingsFeatureComponentHolder.init(dependencies)
        return SettingsFeatureComponentHolder.get()
    }

    @Provides
    fun provideSearchJokeFeature(dependencies: SearchJokeFeatureDependencies): SearchJokeFeatureApi {
        SearchJokeFeatureComponentHolder.init(dependencies)
        return SearchJokeFeatureComponentHolder.get()
    }

    @Provides
    fun provideRandomJokeFeature(dependencies: RandomJokeFeatureDependencies): RandomJokeFeatureApi {
        RandomJokeFeatureComponentHolder.init(dependencies)
        return RandomJokeFeatureComponentHolder.get()
    }
}