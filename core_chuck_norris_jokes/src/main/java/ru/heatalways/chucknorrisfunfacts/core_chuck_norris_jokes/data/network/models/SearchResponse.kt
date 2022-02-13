package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.models

import com.google.gson.annotations.SerializedName

internal data class SearchResponse(
    @SerializedName("total") val total: Int?,
    @SerializedName("result") val result: List<ChuckJokeNetwork>?
)