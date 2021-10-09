package ru.heatalways.chucknorrisfunfacts.presentation.util.appbars

import android.view.LayoutInflater
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.heatalways.chucknorrisfunfacts.databinding.IncAppbarLayoutBinding

class DefaultAppbar(
    @IdRes private val parentLayoutId: Int,
    private val builder: Toolbar.() -> Unit
): Appbar() {
    private var binding: IncAppbarLayoutBinding? = null

    override val toolbar get() = binding?.toolbar

    override fun inflate(fragment: Fragment) {
        binding = IncAppbarLayoutBinding.inflate(
            LayoutInflater.from(fragment.context),
            fragment.view?.findViewById(parentLayoutId),
            true
        )

        binding?.toolbar?.builder()
    }

    override fun destroy() {
        binding = null
    }
}