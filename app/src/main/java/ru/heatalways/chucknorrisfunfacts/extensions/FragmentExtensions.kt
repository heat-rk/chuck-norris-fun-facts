package ru.heatalways.chucknorrisfunfacts.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseActivity

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

fun Fragment.getString(stringResource: StringResource?) =
    stringResource?.getText(requireContext())

fun Fragment.hideKeyboard() {
    baseActivity.hideKeyboard()
}