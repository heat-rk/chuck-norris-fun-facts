package ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes

import ru.heatalways.chucknorrisfunfacts.business.domain.utils.StringResource
import java.util.*

data class ChuckJoke(
    val id: String,
    val categories: List<Category>,
    val createdAt: Date?,
    val updatedAt: Date?,
    val savedAt: Date?,
    val iconUrl: String?,
    val url: String?,
    val value: StringResource
)