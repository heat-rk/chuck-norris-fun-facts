package ru.heatalways.chucknorrisfunfacts.presentation.util.appbars

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

class NoAppBar: Appbar() {
    override val toolbar: Toolbar? get() = null
    override fun inflate(fragment: Fragment) = Unit
    override fun destroy() = Unit
}