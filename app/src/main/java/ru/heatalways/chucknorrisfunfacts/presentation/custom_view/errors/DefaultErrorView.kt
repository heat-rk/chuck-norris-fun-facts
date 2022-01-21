package ru.heatalways.chucknorrisfunfacts.presentation.custom_view.errors

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.IncErrorDefaultBinding
import ru.heatalways.chucknorrisfunfacts.core.models.StringResource
import ru.heatalways.chucknorrisfunfacts.extensions.getString

class DefaultErrorView: FrameLayout, ErrorView {
    private var binding: IncErrorDefaultBinding? = null

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(attrs)
    }


    private fun initView(attrs: AttributeSet?) {
        binding =
            IncErrorDefaultBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
            )
    }


    override fun show(message: StringResource?) {
        isVisible = true

        binding?.errorTextView?.text =
            context.getString(message) ?: context.getString(R.string.error_unknown)
    }

    override fun hide() {
        isVisible = false
        binding?.errorTextView?.text = null
    }
}