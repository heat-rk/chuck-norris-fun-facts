package ru.heatalways.chucknorrisfunfacts.data.network.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("total") val total: Int?,
    @SerializedName("result") val result: List<ChuckJokeNetwork>?
)