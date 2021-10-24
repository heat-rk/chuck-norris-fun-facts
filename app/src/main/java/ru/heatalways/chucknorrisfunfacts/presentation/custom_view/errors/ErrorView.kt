package ru.heatalways.chucknorrisfunfacts.presentation.custom_view.errors

import ru.heatalways.chucknorrisfunfacts.core.utils.StringResource

interface ErrorView {
    fun show(message: StringResource?)
    fun hide()
}