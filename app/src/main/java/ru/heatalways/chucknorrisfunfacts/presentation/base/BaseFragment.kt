package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.BaseFragmentBinding
import ru.heatalways.chucknorrisfunfacts.domain.utils.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.baseActivity
import ru.heatalways.chucknorrisfunfacts.extensions.getString
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity

abstract class BaseFragment<Binding: ViewBinding>: Fragment(), KeyboardChangeListener {
    private lateinit var rootBinding: BaseFragmentBinding
    protected lateinit var binding: Binding

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> Binding
    @ColorRes protected open val backgroundColor = R.color.darkBackgroundColor

    protected var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootBinding = BaseFragmentBinding.inflate(inflater, container, false).apply {
            root.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColor))
            appbar.toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        binding = bindingInflater(inflater, rootBinding.contentContainer, true)

        return rootBinding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.setBottomMenuVisibility(true)
            activity.addKeyboardListener(this)
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.removeKeyboardListener(this)
        }
    }

    protected fun showSnackbar(
        view: View,
        message: StringResource,
        buttonText: StringResource? = null,
        buttonCallback: () -> Unit = {}
    ) {
        snackbar = Snackbar.make(
            view,
            getString(message) ?: getString(R.string.error_unknown),
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            if (buttonText != null)
                setAction(getString(buttonText)) { buttonCallback() }

            show()
        }
    }

    protected fun hideSnackbar() {
        snackbar?.dismiss()
    }

    protected fun setErrorVisibility(isVisible: Boolean, message: StringResource? = null) {
        rootBinding.errorContainer.isVisible = isVisible
        rootBinding.errorTextView.text = getString(message) ?: getString(R.string.error_unknown)
    }

    protected fun setProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) rootBinding.errorContainer.isVisible = false
        rootBinding.progressBar.isVisible = isVisible
    }

    protected fun setTitle(@StringRes title: Int) {
        rootBinding.appbar.toolbar.setTitle(title)
    }

    protected fun setTitle(title: String) {
        rootBinding.appbar.toolbar.title = title
    }

    protected fun initToolbarBackButton() {
        rootBinding.appbar.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_arrow)

        rootBinding.appbar.toolbar.navigationContentDescription =
            getString(R.string.navigation_back)
    }

    protected fun initMenu(@MenuRes menuRes: Int) {
        rootBinding.appbar.toolbar.inflateMenu(menuRes)
        rootBinding.appbar.toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
    }

    override fun onKeyboardChanged(isOpen: Boolean) {
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.setBottomMenuVisibility(!isOpen)
        }
    }
}