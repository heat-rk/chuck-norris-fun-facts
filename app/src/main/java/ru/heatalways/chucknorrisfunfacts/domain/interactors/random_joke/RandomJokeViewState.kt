package ru.heatalways.chucknorrisfunfacts.domain.interactors.random_joke

import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.MviState

data class RandomJokeViewState(
    val isLoading: Boolean = false,
    val isJokeLoading: Boolean = false,
    val message: StringResource? = null,
    val jokes: List<ChuckJoke> = emptyList(),
    val category: Category = Category.Any
): MviState