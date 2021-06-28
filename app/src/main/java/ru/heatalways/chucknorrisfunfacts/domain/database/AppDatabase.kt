package ru.heatalways.chucknorrisfunfacts.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.database.converters.StringListConverter
import ru.heatalways.chucknorrisfunfacts.domain.database.dao.SavedJokesDao

@Database(entities = [ChuckJoke::class], version = AppDatabase.VERSION)

@TypeConverters(StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun savedJokesDao(): SavedJokesDao

    companion object {
        const val NAME = "appDatabase"
        const val VERSION = 1
    }
}