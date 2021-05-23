package ru.heatalways.chucknorrisfunfacts.data.entities

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total") val total: Int?,
    @SerializedName("result") val result: List<ChuckJoke>?
)