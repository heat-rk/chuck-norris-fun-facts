package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
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
    @IdRes protected open val contentId = R.id.contentContainer
    @ColorRes protected open val backgroundColor = R.color.darkBackgroundColor

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

    protected fun setErrorVisibility(isVisible: Boolean, message: StringResource? = null) {
        rootBinding.root.findViewById<View>(contentId)?.isVisible = !isVisible
        rootBinding.errorContainer.isVisible = isVisible
        rootBinding.errorTextView.text = getString(message) ?: getString(R.string.error_unknown)
    }

    protected fun setProgressBarVisibility(isVisible: Boolean) {
        rootBinding.root.findViewById<View>(contentId)?.isVisible = !isVisible
        if (isVisible) rootBinding.errorContainer.isVisible = false
        rootBinding.progressBar.isVisible = isVisible
    }

    protected fun setTitle(@StringRes title: Int) {
        rootBinding.appbar.toolbar.setTitle(title)
    }

    protected fun setTitle(title: String) {
        rootBinding.appbar.toolbar.title = title
    }

    override fun onKeyboardChanged(isOpen: Boolean) {
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.setBottomMenuVisibility(!isOpen)
        }
    }
}