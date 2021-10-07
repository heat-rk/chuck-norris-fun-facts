package ru.heatalways.chucknorrisfunfacts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.heatalways.chucknorrisfunfacts.data.database.InMemoryDatabase.Companion.VERSION
import ru.heatalways.chucknorrisfunfacts.data.database.converters.StringListConverter
import ru.heatalways.chucknorrisfunfacts.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.database.trash_jokes.TrashJokesDao

@Database(entities = [ChuckJokeEntity::class], version = VERSION)
@TypeConverters(StringListConverter::class)
abstract class InMemoryDatabase: RoomDatabase() {
    abstract fun trashJokesDao(): TrashJokesDao

    companion object {
        const val VERSION = 1
    }
}