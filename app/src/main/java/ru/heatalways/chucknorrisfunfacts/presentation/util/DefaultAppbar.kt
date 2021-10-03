package ru.heatalways.chucknorrisfunfacts.presentation.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.emptyFlow
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.IncAppbarLayoutBinding
import ru.ldralighieri.corbind.appcompat.itemClicks

class DefaultAppbar(lifecycleOwner: LifecycleOwner): LifecycleEventObserver {
    private var binding: IncAppbarLayoutBinding? = null
    private var context: Context? = null

    val toolbarItemClicks
        get() = binding?.toolbar?.itemClicks() ?: emptyFlow()

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            binding = null
            context = null
        }
    }

    fun inflate(
        context: Context,
        activity: Activity,
        parentLayout: ViewGroup
    ) {
        this.context = context

        binding = IncAppbarLayoutBinding.inflate(
            LayoutInflater.from(context),
            parentLayout,
            true
        ).apply {
            toolbar.setNavigationOnClickListener {
                activity.onBackPressed()
            }
        }
    }

    fun setTitle(@StringRes title: Int) {
        binding?.toolbar?.setTitle(title)
    }

    fun setTitle(title: String) {
        binding?.toolbar?.title = title
    }

    fun initBackButton() {
        context?.let { safeContext ->
            binding?.toolbar?.navigationIcon =
                ContextCompat.getDrawable(safeContext, R.drawable.ic_back_arrow)

            binding?.toolbar?.navigationContentDescription =
                safeContext.getString(R.string.navigation_back)
        }
    }

    fun initMenu(@MenuRes menuRes: Int) {
        binding?.toolbar?.inflateMenu(menuRes)
    }
}