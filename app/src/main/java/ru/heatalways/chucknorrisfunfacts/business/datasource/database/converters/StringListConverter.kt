package ru.heatalways.chucknorrisfunfacts.business.datasource.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(str: String?): List<String> {
        if(str == null)
            return emptyList()

        return Gson().fromJson(
            str,
            object : TypeToken<List<String>>() {}.type
        )
    }
}