package ru.heatalways.chucknorrisfunfacts.domain.models

import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import java.util.*

data class ChuckJoke(
    val actualId: Long,
    val id: String,
    val categories: List<Category>,
    val createdAt: Date?,
    val updatedAt: Date?,
    val savedAt: Date?,
    val iconUrl: String?,
    val url: String?,
    val value: StringResource,
    var isUpdating: Boolean = false
)