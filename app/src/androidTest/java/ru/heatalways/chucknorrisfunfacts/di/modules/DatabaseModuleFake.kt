package ru.heatalways.chucknorrisfunfacts.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.heatalways.chucknorrisfunfacts.data.database.AppDatabase

@Module
object DatabaseModuleFake {
    @Provides
    fun provideTestAppDatabase(applicationContext: Context) =
        Room.inMemoryDatabaseBuilder(applicationContext, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}