package ru.heatalways.chucknorrisfunfacts.presentation.custom_view.errors

import ru.heatalways.chucknorrisfunfacts.core.models.StringResource

interface ErrorView {
    fun show(message: StringResource?)
    fun hide()
}