package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import ru.heatalways.chucknorrisfunfacts.App

abstract class BaseViewModel: ViewModel() {
    protected fun getString(@StringRes stringRes: Int)
            = App.instance.getString(stringRes)

    protected fun getString(@StringRes stringRes: Int, intValue: Int)
            = App.instance.getString(stringRes, intValue)

    protected fun getString(@StringRes stringRes: Int, stringValue: String)
            = App.instance.getString(stringRes, stringValue)

    protected fun getQuantityString(@PluralsRes id: Int, quantity: Int)
            = App.instance.resources.getQuantityString(id, quantity, quantity)

    protected fun getResources(): Resources? = App.instance.resources

    protected fun getDrawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(App.instance, drawableRes)
}