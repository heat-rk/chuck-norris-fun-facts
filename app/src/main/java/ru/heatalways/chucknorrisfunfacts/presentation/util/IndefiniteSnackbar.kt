package ru.heatalways.chucknorrisfunfacts.presentation.util

import android.view.View
import androidx.annotation.IdRes
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.getString

class IndefiniteSnackbar {
    private var snackbar: Snackbar? = null

    private val _actions = MutableSharedFlow<Unit>()
    val actions = _actions.asSharedFlow()

    fun show(
        view: View,
        @IdRes anchorView: Int? = null,
        message: StringResource,
        buttonText: StringResource? = null,
        coroutineScope: CoroutineScope
    ) {
        val context = view.context
        snackbar = Snackbar.make(
            view,
            context.getString(message) ?: context.getString(R.string.error_unknown),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            if (buttonText != null)
                setAction(context.getString(buttonText)) {
                    coroutineScope.launch {
                        _actions.emit(Unit)
                    }
                }

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