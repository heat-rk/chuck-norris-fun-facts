package ru.heatalways.chucknorrisfunfacts.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource

fun Fragment.showToast(message: StringResource?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message?.toCharSequence(requireContext()) ?: getString(R.string.error_unknown), length).show()
}

fun Fragment.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message ?: getString(R.string.error_unknown), length).show()
}

fun Fragment.showKeyboard(viewId: Int) {
    activity?.showKeyboard(viewId)
}

fun Context.getString(stringResource: StringResource?) =
    stringResource?.toCharSequence(this)

fun Fragment.getString(stringResource: StringResource?) =
    stringResource?.toCharSequence(requireContext())

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}