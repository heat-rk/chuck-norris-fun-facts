package ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.mappers

import ru.heatalways.chucknorrisfunfacts.core.data.database.models.ChuckJokeEntity
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.R
import ru.heatalways.chucknorrisfunfacts.core_chuck_norris_jokes.data.network.models.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.common.domain.utils.DateUtils
import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.common.general.utils.strRes
import java.util.*

internal fun ChuckJokeNetwork.toDomain() = ChuckJoke(
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

internal fun ChuckJokeEntity.toDomain() = ChuckJoke(
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

internal fun ChuckJoke.toEntity() = ChuckJokeEntity(
    id = this.id,
    categories = this.categories.mapNotNull { if (it is Category.Specific) it.name else null },
    createdAt = this.createdAt?.time,
    updatedAt = this.updatedAt?.time,
    savedAt = this.savedAt?.time,
    iconUrl = this.iconUrl,
    url = this.url,
    value = (this.value as? StringResource.ByString)?.text
)