package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.BaseFragmentBinding
import ru.heatalways.chucknorrisfunfacts.extensions.hideKeyboard
import ru.heatalways.chucknorrisfunfacts.presentation.util.appbars.Appbar
import ru.heatalways.chucknorrisfunfacts.presentation.util.appbars.NoAppBar

abstract class BindingFragment<Binding: ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> Binding
): Fragment() {
    private var _rootBinding: BaseFragmentBinding? = null
    private var _binding: Binding? = null

    val rootBinding
        get() = _rootBinding!!

    val binding
        get() = _binding!!

    @ColorRes protected open val backgroundColor = R.color.darkBackgroundColor
    protected open val appbar: Appbar = NoAppBar()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appbar.inflate(this)
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroyView() {
        _binding = null
        _rootBinding = null
        appbar.destroy()
        super.onDestroyView()
    }
}