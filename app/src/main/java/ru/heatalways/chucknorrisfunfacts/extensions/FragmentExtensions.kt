package ru.heatalways.chucknorrisfunfacts.extensions

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseActivity
import ru.heatalways.chucknorrisfunfacts.presentation.util.TrackedReference

val Fragment.baseActivity get() = activity as BaseActivity<*>

fun Fragment.showToast(message: StringResource?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message?.getText(requireContext()) ?: getString(R.string.error_unknown), length).show()
}

fun Fragment.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message ?: getString(R.string.error_unknown), length).show()
}

fun Fragment.showKeyboard(viewId: Int) {
    baseActivity.showKeyboard(viewId)
}

fun Context.getString(stringResource: StringResource?) =
    stringResource?.getText(this)

fun Fragment.getString(stringResource: StringResource?) =
    stringResource?.getText(requireContext())

fun Fragment.hideKeyboard() {
    baseActivity.hideKeyboard()
}

fun <T: Any> Fragment.getTrackedArgument(key: String) =
    arguments?.getTrackedReference<T>(key)

fun <T: Any> Fragment.getSafeTrackedArgument(key: String) =
    arguments?.getTrackedReference<T>(key)?.safeGet()

fun <T: Any> Fragment.putTrackedReference(key: String, value: T) {
    if (arguments == null) arguments = Bundle()
    arguments?.putTrackedReference(key, value)
}