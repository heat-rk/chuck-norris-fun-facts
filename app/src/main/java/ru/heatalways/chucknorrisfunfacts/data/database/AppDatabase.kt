package ru.heatalways.chucknorrisfunfacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.heatalways.chucknorrisfunfacts.data.database.converters.StringListConverter
import ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes.SavedJokesDao

@Database(entities = [ChuckJokeEntity::class], version = AppDatabase.VERSION)

@TypeConverters(StringListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun savedJokesDao(): SavedJokesDao

    companion object {
        const val NAME = "appDatabase"
        const val VERSION = 2
    }
}