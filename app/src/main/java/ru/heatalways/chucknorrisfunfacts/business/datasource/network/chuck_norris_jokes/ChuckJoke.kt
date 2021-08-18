package ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = ChuckJoke.TABLE_NAME)
data class ChuckJoke(
    @SerializedName("categories")
    @ColumnInfo(name = CATEGORIES)
    val categories: List<String>?,

    @SerializedName("created_at")
    @ColumnInfo(name = CREATED_AT)
    val createdAt: String?,

    @SerializedName("icon_url")
    @ColumnInfo(name = ICON_URL)
    val iconUrl: String?,

    @SerializedName("id")
    @ColumnInfo(name = ID)
    @PrimaryKey
    val id: String,

    @SerializedName("updated_at")
    @ColumnInfo(name = UPDATED_AT)
    val updatedAt: String?,

    @SerializedName("url")
    @ColumnInfo(name = URL)
    val url: String?,

    @SerializedName("value")
    @ColumnInfo(name = VALUE)
    val value: String?,

    @ColumnInfo(name = SAVED_AT)
    var savedAt: Long?
) {
    companion object {
        const val TABLE_NAME = "db_chuck_jokes"

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