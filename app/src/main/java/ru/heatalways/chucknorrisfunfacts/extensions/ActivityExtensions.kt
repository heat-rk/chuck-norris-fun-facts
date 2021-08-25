package ru.heatalways.chucknorrisfunfacts.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    view.requestFocus()
    inputMethodManager?.showSoftInput(view, 0)
}

fun AppCompatActivity.showKeyboard(viewId: Int) {
    val view = findViewById<View>(viewId)
    showKeyboard(view ?: return)
}

fun AppCompatActivity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}