package ru.heatalways.chucknorrisfunfacts.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.presentation.screen.main.MainActivity

abstract class BaseFragment<Binding: ViewBinding>: Fragment(), KeyboardChangeListener {
    private lateinit var rootView: ViewGroup

    private var mBinding: Binding? = null
    protected val binding get() = mBinding!!

    private val baseActivity get() = activity as BaseActivity<*>

    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    @IdRes protected open val contentId = R.id.contentContainer
    @ColorRes protected open val backgroundColor = R.color.darkBackgroundColor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.base_fragment, container, false)
                as ViewGroup

        val contentContainer = rootView.findViewById<ViewGroup>(R.id.contentContainer)

        mBinding = getBinding(
            inflater = inflater,
            container = container
        )

        contentContainer.addView(binding.root)

        rootView.setBackgroundColor(
            ContextCompat.getColor(requireContext(), backgroundColor)
        )

        rootView.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
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

    fun showKeyboard(viewId: Int) {
        baseActivity.showKeyboard(viewId)
    }

    fun hideKeyboard() {
        baseActivity.hideKeyboard()
    }

    protected fun <T> observe(liveData: LiveData<T>, callback: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner, callback)
    }

    protected fun showMessage(message: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, length).show()
    }

    protected fun setErrorVisibility(isVisible: Boolean, message: String? = null) {
        rootView.findViewById<View>(contentId)?.setVisibleOrGone(!isVisible)
        rootView.findViewById<ViewGroup>(R.id.errorContainer).setVisibleOrGone(isVisible)
        rootView.findViewById<TextView>(R.id.errorTextView).text = message ?: getString(R.string.error_unknown)
    }

    protected fun setProgressBarVisibility(isVisible: Boolean) {
        rootView.findViewById<View>(contentId)?.setVisibleOrGone(!isVisible)
        rootView.findViewById<ViewGroup>(R.id.errorContainer).setVisibleOrGone(!isVisible)
        rootView.findViewById<ProgressBar>(R.id.progressBar).setVisibleOrGone(isVisible)
    }

    protected fun setTitle(@StringRes title: Int) {
        rootView.findViewById<Toolbar>(R.id.toolbar)?.setTitle(title)
    }

    protected fun setTitle(title: String) {
        rootView.findViewById<Toolbar>(R.id.toolbar)?.title = title
    }

    override fun onKeyboardChanged(isOpen: Boolean) {
        val activity = baseActivity
        if (activity is MainActivity) {
            activity.setBottomMenuVisibility(!isOpen)
        }
    }
}