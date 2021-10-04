package ru.heatalways.chucknorrisfunfacts.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
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

fun flowTimer(millis: Long) = flow {
    kotlinx.coroutines.delay(millis)
    emit(Unit)
}

fun <T> Flow<T>.mergeWith(flow: Flow<T>) = merge(this, flow)