package ru.heatalways.chucknorrisfunfacts.presentation.util

import android.view.View
import androidx.annotation.IdRes
import com.google.android.material.snackbar.Snackbar
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.getString

class IndefiniteSnackbar {
    private var snackbar: Snackbar? = null

    fun show(
        view: View,
        @IdRes anchorView: Int? = null,
        message: StringResource,
        buttonText: StringResource? = null,
        buttonCallback: () -> Unit = {}
    ) {
        val context = view.context
        snackbar = Snackbar.make(
            view,
            context.getString(message) ?: context.getString(R.string.error_unknown),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            if (buttonText != null)
                setAction(context.getString(buttonText)) { buttonCallback() }

            if (anchorView != null)
                setAnchorView(anchorView)

            show()
        }
    }

    fun hide() {
        snackbar?.dismiss()
        snackbar = null
    }
}