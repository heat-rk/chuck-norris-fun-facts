package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
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
    private var _rootBinding: BaseFragmentBinding? = null
    private var _binding: Binding? = null

    val rootBinding
        get() = _rootBinding!!

    val binding
        get() = _binding!!

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> Binding
    @ColorRes protected open val backgroundColor = R.color.darkBackgroundColor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _rootBinding = BaseFragmentBinding.inflate(inflater, container, false).apply {
            root.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColor))
        }

        _binding = bindingInflater(inflater, rootBinding.contentContainer, true)

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

    override fun onDestroyView() {
        _binding = null
        _rootBinding = null
        super.onDestroyView()
    }

    protected fun setErrorVisibility(isVisible: Boolean, message: StringResource? = null) {
        rootBinding.errorContainer.isVisible = isVisible
        rootBinding.errorTextView.text = getString(message) ?: getString(R.string.error_unknown)
    }

    protected fun setProgressBarVisibility(isVisible: Boolean) {
        rootBinding.progressBar.isVisible = isVisible
    }

    override fun onKeyboardChanged(isOpen: Boolean) {
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.setBottomMenuVisibility(!isOpen)
        }
    }
}