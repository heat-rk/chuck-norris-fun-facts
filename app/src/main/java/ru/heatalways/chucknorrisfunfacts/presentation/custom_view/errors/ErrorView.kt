package ru.heatalways.chucknorrisfunfacts.presentation.custom_view.errors

import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource

interface ErrorView {
    fun show(message: StringResource?)
    fun hide()
}