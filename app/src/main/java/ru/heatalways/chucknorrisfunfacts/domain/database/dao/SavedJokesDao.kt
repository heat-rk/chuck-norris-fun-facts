package ru.heatalways.chucknorrisfunfacts.domain.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke

@Dao
interface SavedJokesDao {
    @Query("SELECT * FROM ${ChuckJoke.TABLE_NAME}")
    suspend fun getAll(): List<ChuckJoke>

    @Query("SELECT * FROM ${ChuckJoke.TABLE_NAME} LIMIT :limit OFFSET :offset")
    suspend fun getBy(limit: Int = 10, offset: Int = 0): List<ChuckJoke>

    @Insert
    suspend fun insert(joke: ChuckJoke)
}