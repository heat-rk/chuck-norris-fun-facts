package ru.heatalways.chucknorrisfunfacts.mappers

import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.database.saved_jokes.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.network.chuck_norris_jokes.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.DateUtils
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.domain.utils.strRes
import java.util.*

fun ChuckJokeNetwork.toDomain() = ChuckJoke(
    id = this.id,
    categories = this.categories?.map { Category.Specific(it) } ?: emptyList(),
    createdAt = this.createdAt?.let { DateUtils.toDate(it, DateUtils.Format.Default) },
    updatedAt = this.updatedAt?.let { DateUtils.toDate(it, DateUtils.Format.Default) },
    savedAt = null,
    iconUrl = this.iconUrl,
    url = this.url,
    value = this.value?.let { strRes(it) } ?: strRes(R.string.empty_field)
)

fun ChuckJokeEntity.toDomain() = ChuckJoke(
    id = this.id,
    categories = this.categories?.map { Category.Specific(it) } ?: emptyList(),
    createdAt = this.createdAt?.let { Date(it) },
    updatedAt = this.updatedAt?.let { Date(it) },
    savedAt = this.savedAt?.let { Date(it) },
    iconUrl = this.iconUrl,
    url = this.url,
    value = this.value?.let { strRes(it) } ?: strRes(R.string.empty_field)
)

fun ChuckJoke.toEntity() = ChuckJokeEntity(
    id = this.id,
    categories = this.categories.mapNotNull { if (it is Category.Specific) it.name else null },
    createdAt = this.createdAt?.time,
    updatedAt = this.updatedAt?.time,
    savedAt = this.savedAt?.time,
    iconUrl = this.iconUrl,
    url = this.url,
    value = if (this.value is StringResource.ByString) this.value.text else null
)