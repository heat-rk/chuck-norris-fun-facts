package ru.heatalways.chucknorrisfunfacts.data.database.trash_jokes

import androidx.room.*
import ru.heatalways.chucknorrisfunfacts.data.database.models.ChuckJokeEntity

@Dao
interface TrashJokesDao {
    @Query("SELECT * FROM ${ChuckJokeEntity.TABLE_NAME} ORDER BY ${ChuckJokeEntity.SAVED_AT} DESC")
    suspend fun getAll(): List<ChuckJokeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(joke: List<ChuckJokeEntity>)

    @Query("DELETE FROM ${ChuckJokeEntity.TABLE_NAME}")
    suspend fun clear(): Int
}