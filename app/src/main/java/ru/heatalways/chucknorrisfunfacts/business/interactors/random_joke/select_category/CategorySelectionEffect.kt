package ru.heatalways.chucknorrisfunfacts.business.interactors.random_joke.select_category

import ru.heatalways.chucknorrisfunfacts.presentation.base.MviEffect

sealed class CategorySelectionEffect: MviEffect {
    object GoBack: CategorySelectionEffect()
}