package ru.heatalways.chucknorrisfunfacts.mappers

import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.data.network.models.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.domain.utils.DateUtils
import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.core.utils.strRes
import java.util.*

fun ChuckJokeNetwork.toDomain() = ChuckJoke(
    actualId = this.id.hashCode().toLong(),
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
    actualId = this.actualId,
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
