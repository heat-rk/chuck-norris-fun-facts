package ru.heatalways.chucknorrisfunfacts.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.MIGRATION_1_2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.NAME
    )
        .addMigrations(MIGRATION_1_2)
        .build()
}