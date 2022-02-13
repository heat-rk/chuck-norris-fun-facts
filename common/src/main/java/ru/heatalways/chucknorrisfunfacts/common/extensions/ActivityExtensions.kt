package ru.heatalways.chucknorrisfunfacts.common.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    view.requestFocus()
    inputMethodManager?.showSoftInput(view, 0)
}

fun Activity.showKeyboard(viewId: Int) {
    val view = findViewById<View>(viewId)
    showKeyboard(view ?: return)
}

fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}