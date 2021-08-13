package ru.heatalways.chucknorrisfunfacts.presentation.base

import androidx.lifecycle.ViewModel
import ru.heatalways.chucknorrisfunfacts.data.utils.StringResource

abstract class BaseViewModel: ViewModel() {
    protected fun strRes(text: String?) = StringResource.ByString(text)
    protected fun strRes(text: Int) = StringResource.ByRes(text)
}