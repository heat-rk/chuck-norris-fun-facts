package ru.heatalways.chucknorrisfunfacts.core.presentation.custom_view.search_query_view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import ru.heatalways.chucknorrisfunfacts.core.R
import ru.heatalways.chucknorrisfunfacts.core.databinding.IncSearchLayoutBinding
import ru.heatalways.chucknorrisfunfacts.common.extensions.maxLength
import ru.heatalways.chucknorrisfunfacts.common.extensions.showHideSmoothly

class SearchQueryView: FrameLayout {
    private var binding: IncSearchLayoutBinding? = null
    private var minQueryLength: Int = 0
    private var maxQueryLength: Int = Int.MAX_VALUE
    private var textWatcher: TextWatcher? = null


    val queryLength
        get() = binding?.searchQueryEditText?.length() ?: 0

    val isQueryLengthValid
        get() = queryLength in minQueryLength..maxQueryLength

    val searchQuery
        get() = binding?.searchQueryEditText?.text.toString()

    var onSearchExecute: ((query: String) -> Unit)? = null

    var onQueryChanged: ((query: String) -> Unit)? = null



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
        setSearchButtonVisibility(attributes.getBoolean(R.styleable.SearchQueryView_searchButtonVisible, true))
        setMaxQueryLength(attributes.getInteger(R.styleable.SearchQueryView_maxQueryLength, Int.MAX_VALUE))
        setMinQueryLength(attributes.getInteger(R.styleable.SearchQueryView_minQueryLength, 0))

        attributes.recycle()

        initListeners()
    }

    private fun initListeners() {
        binding?.apply {
            searchQueryEditText.apply {
                textWatcher = doAfterTextChanged {
                    clearButton.showHideSmoothly(queryLength > 0)
                    onQueryChanged?.invoke(searchQuery)
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

            clearButton.setOnClickListener {
                searchQueryEditText.setText("")
            }
        }
    }

    fun setHint(hint: CharSequence?) {
        binding?.searchQueryEditText?.hint = hint ?: ""
    }

    fun setSearchButtonVisibility(isVisible: Boolean) {
        binding?.searchButton?.isVisible = isVisible
    }

    fun setMaxQueryLength(length: Int) {
        maxQueryLength = length
        binding?.searchQueryEditText?.maxLength(length)
    }

    fun setMinQueryLength(length: Int) {
        minQueryLength = length
    }

    fun searches(): Flow<String> = channelFlow {
        onSearchExecute = { if (isActive) trySend(it) }
        awaitClose { onSearchExecute = null }
    }

    fun queryChanges(): Flow<String> = channelFlow {
        onQueryChanged = { if (isActive) trySend(it) }
        awaitClose { onQueryChanged = null }
    }

    fun clearListeners() {
        binding?.searchQueryEditText?.removeTextChangedListener(textWatcher)
        binding?.searchButton?.setOnClickListener(null)
        onSearchExecute = null
        textWatcher = null
    }

    companion object {
        const val DEFAULT_DEBOUNCE_TIME = 300L
    }
}