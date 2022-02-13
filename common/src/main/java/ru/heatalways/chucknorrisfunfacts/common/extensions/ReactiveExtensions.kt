package ru.heatalways.chucknorrisfunfacts.common.extensions

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.CompoundButton
import androidx.annotation.CheckResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import ru.ldralighieri.corbind.recyclerview.scrollEvents

fun RecyclerView.scrollsToLastItem() = scrollEvents()
    .filter {
        val visibleItemCount =
            (it.view.layoutManager as LinearLayoutManager).childCount

        val totalItemCount =
            (it.view.layoutManager as LinearLayoutManager).itemCount

        val firstVisibleItemPosition =
            (it.view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                firstVisibleItemPosition >= 0
    }

@SuppressLint("ClickableViewAccessibility")
@ExperimentalCoroutinesApi
@CheckResult
fun CompoundButton.checkedChangesIntents(): Flow<Boolean> = channelFlow {
    var isUIControlled = false

    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            isUIControlled = true
        }

        return@setOnTouchListener false
    }

    setOnCheckedChangeListener { button, isChecked ->
        if (isActive) {
            if (isUIControlled) {
                button.isChecked = !isChecked
                isUIControlled = false
                trySend(isChecked)
            }
        }
    }

    awaitClose {
        setOnCheckedChangeListener(null)
        setOnTouchListener(null)
    }
}

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var windowStartTime = System.currentTimeMillis()
    var emitted = false
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - windowStartTime
        if (delta >= windowDuration) {
            windowStartTime += delta / windowDuration * windowDuration
            emitted = false
        }
        if (!emitted) {
            emit(value)
            emitted = true
        }
    }
}

fun <T> Flow<T>.debounceFirst(windowDuration: Long): Flow<T> = flow {
    var windowStartTime = System.currentTimeMillis()
    var emitted = false
    collect { value ->
        val currentTime = System.currentTimeMillis()
        val delta = currentTime - windowStartTime
        if (delta >= windowDuration) {
            windowStartTime += delta / windowDuration * windowDuration
            emitted = false
        }
        if (!emitted) {
            emit(value)
            emitted = true
        }
        if (emitted && delta < windowDuration) {
            windowStartTime = System.currentTimeMillis()
        }
    }
}

fun flowTimer(millis: Long) = flow {
    kotlinx.coroutines.delay(millis)
    emit(Unit)
}

fun <T> Flow<T>.mergeWith(flow: Flow<T>) = merge(this, flow)