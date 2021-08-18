package ru.heatalways.chucknorrisfunfacts.business.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.converters.StringListConverter
import ru.heatalways.chucknorrisfunfacts.business.datasource.database.saved_jokes.SavedJokesDao

@Database(entities = [ChuckJoke::class], version = AppDatabase.VERSION)

@TypeConverters(StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun savedJokesDao(): SavedJokesDao

    companion object {
        const val NAME = "appDatabase"
        const val VERSION = 1
    }
}