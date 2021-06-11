package ru.heatalways.chucknorrisfunfacts.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.IncSearchLayoutBinding
import ru.heatalways.chucknorrisfunfacts.extensions.maxLength
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.extensions.showHideSmoothly

class SearchQueryView: FrameLayout {
    private var binding: IncSearchLayoutBinding? = null
    private var minQueryLength: Int = 0
    private var maxQueryLength: Int = Int.MAX_VALUE

    val isQueryLengthValid
        get() = binding?.searchQueryEditText?.length() in minQueryLength..maxQueryLength

    val searchQuery
        get() = binding?.searchQueryEditText?.text.toString()

    var onSearchExecute: (query: String) -> Unit = {}

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        binding = IncSearchLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SearchQueryView)

        setHint(attributes.getText(R.styleable.SearchQueryView_hint))
        setSearchButtonVisibility(attributes.getBoolean(R.styleable.SearchQueryView_search_button_visible, false))
        setMaxQueryLength(attributes.getInteger(R.styleable.SearchQueryView_maxQueryLength, Int.MAX_VALUE))
        setMinQueryLength(attributes.getInteger(R.styleable.SearchQueryView_minQueryLength, 0))

        attributes.recycle()

        initListeners()
    }

    private fun initListeners() {
        binding?.apply {
            searchQueryEditText.apply {
                doAfterTextChanged {
                    searchButton.showHideSmoothly(isQueryLengthValid)
                }

                setOnEditorActionListener { _, actionId,_ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH && isQueryLengthValid) {
                        onSearchExecute(searchQuery)
                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
            }

            searchButton.setOnClickListener {
                onSearchExecute(searchQuery)
            }
        }
    }

    fun setHint(hint: CharSequence?) {
        binding?.searchQueryEditText?.hint = hint ?: ""
    }

    fun setSearchButtonVisibility(isVisible: Boolean) {
        binding?.searchButton?.setVisibleOrGone(isVisible)
    }

    fun setMaxQueryLength(length: Int) {
        maxQueryLength = length
        binding?.searchQueryEditText?.maxLength(length)
    }

    fun setMinQueryLength(length: Int) {
        minQueryLength = length
    }
}