package ru.heatalways.chucknorrisfunfacts.core.presentation.custom_view.errors

import ru.heatalways.chucknorrisfunfacts.common.general.utils.StringResource

interface ErrorView {
    fun show(message: StringResource?)
    fun hide()
}