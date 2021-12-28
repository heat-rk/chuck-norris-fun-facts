package ru.heatalways.chucknorrisfunfacts.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase
import ru.heatalways.chucknorrisfunfacts.data.database.MIGRATION_1_2
import ru.heatalways.chucknorrisfunfacts.data.database.MIGRATION_2_3
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.NAME
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
}