package ru.heatalways.chucknorrisfunfacts.presentation.custom_view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.IncSearchLayoutBinding
import ru.heatalways.chucknorrisfunfacts.extensions.maxLength
import ru.heatalways.chucknorrisfunfacts.extensions.setVisibleOrGone
import ru.heatalways.chucknorrisfunfacts.extensions.showHideSmoothly

class SearchQueryView: FrameLayout {
    private var binding: IncSearchLayoutBinding? = null
    private var minQueryLength: Int = 0
    private var maxQueryLength: Int = Int.MAX_VALUE

    private var textWatcher: TextWatcher? = null

    val isQueryLengthValid
        get() = binding?.searchQueryEditText?.length() in minQueryLength..maxQueryLength

    val searchQuery
        get() = binding?.searchQueryEditText?.text.toString()

    var onSearchExecute: ((query: String) -> Unit)? = null

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
                textWatcher = doAfterTextChanged {
                    searchButton.showHideSmoothly(isQueryLengthValid)
                }

                setOnEditorActionListener { _, actionId,_ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH && isQueryLengthValid) {
                        onSearchExecute?.invoke(searchQuery)
                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
            }

            searchButton.setOnClickListener {
                onSearchExecute?.invoke(searchQuery)
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

    fun searches(): Flow<String> = channelFlow {
        onSearchExecute = { if (isActive) { trySend(it) } }
        awaitClose { onSearchExecute = {} }
    }

    fun clearListeners() {
        binding?.searchQueryEditText?.removeTextChangedListener(textWatcher)
        binding?.searchButton?.setOnClickListener(null)
        onSearchExecute = null
        textWatcher = null
    }
}