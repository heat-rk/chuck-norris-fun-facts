package ru.heatalways.chucknorrisfunfacts.domain.database.dao

import androidx.room.*
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke

@Dao
interface SavedJokesDao {
    @Query("SELECT * FROM ${ChuckJoke.TABLE_NAME} ORDER BY ${ChuckJoke.SAVED_AT} DESC")
    suspend fun getAll(): List<ChuckJoke>

    @Query("SELECT * FROM ${ChuckJoke.TABLE_NAME} ORDER BY ${ChuckJoke.SAVED_AT} DESC LIMIT :limit OFFSET :offset")
    suspend fun getBy(limit: Int = 10, offset: Int = 0): List<ChuckJoke>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: ChuckJoke)

    @Delete
    suspend fun delete(joke: ChuckJoke)
}