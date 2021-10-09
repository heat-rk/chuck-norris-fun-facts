package ru.heatalways.chucknorrisfunfacts.presentation.util.appbars

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.emptyFlow
import ru.ldralighieri.corbind.appcompat.itemClicks

abstract class Appbar {
    abstract val toolbar: Toolbar?

    val toolbarItemClicks get() = toolbar?.itemClicks() ?: emptyFlow()

    abstract fun inflate(fragment: Fragment)
    abstract fun destroy()
}