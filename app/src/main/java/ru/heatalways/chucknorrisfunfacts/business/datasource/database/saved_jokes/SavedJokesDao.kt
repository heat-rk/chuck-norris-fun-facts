package ru.heatalways.chucknorrisfunfacts.business.datasource.database.saved_jokes

import androidx.room.*
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJokeNetwork

@Dao
interface SavedJokesDao {
    @Query("SELECT * FROM ${ChuckJokeEntity.TABLE_NAME} ORDER BY ${ChuckJokeEntity.SAVED_AT} DESC")
    suspend fun getAll(): List<ChuckJokeEntity>

    @Query("SELECT * FROM ${ChuckJokeEntity.TABLE_NAME} ORDER BY ${ChuckJokeEntity.SAVED_AT} DESC LIMIT :limit OFFSET :offset")
    suspend fun getBy(limit: Int = 10, offset: Int = 0): List<ChuckJokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: ChuckJokeEntity)

    @Delete
    suspend fun delete(joke: ChuckJokeEntity)
}