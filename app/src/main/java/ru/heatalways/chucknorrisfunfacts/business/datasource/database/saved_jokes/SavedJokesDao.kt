package ru.heatalways.chucknorrisfunfacts.business.datasource.database.saved_jokes

import androidx.room.*
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJoke

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