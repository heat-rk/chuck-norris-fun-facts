package ru.heatalways.chucknorrisfunfacts.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.AppDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModuleFake {
    @Provides
    @Named("test_app_db")
    fun provideTestAppDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}