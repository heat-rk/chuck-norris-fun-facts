package ru.heatalways.chucknorrisfunfacts.core.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ChuckJokeEntity.TABLE_NAME)
data class ChuckJokeEntity(
    @ColumnInfo(name = ACTUAL_ID)
    @PrimaryKey(autoGenerate = true)
    val actualId: Long = 0,

    @ColumnInfo(name = CATEGORIES)
    val categories: List<String>?,

    @ColumnInfo(name = CREATED_AT)
    val createdAt: Long?,

    @ColumnInfo(name = ICON_URL)
    val iconUrl: String?,

    @ColumnInfo(name = ID)
    val id: String,

    @ColumnInfo(name = UPDATED_AT)
    val updatedAt: Long?,

    @ColumnInfo(name = URL)
    val url: String?,

    @ColumnInfo(name = VALUE)
    val value: String?,

    @ColumnInfo(name = SAVED_AT)
    val savedAt: Long?
) {
    companion object {
        const val TABLE_NAME = "db_chuck_jokes"

        const val ACTUAL_ID = "actual_id"
        const val CATEGORIES = "categories"
        const val CREATED_AT = "created_at"
        const val ICON_URL = "icon_url"
        const val ID = "id"
        const val UPDATED_AT = "updated_at"
        const val URL = "url"
        const val VALUE = "value"
        const val SAVED_AT = "saved_at"
    }
}