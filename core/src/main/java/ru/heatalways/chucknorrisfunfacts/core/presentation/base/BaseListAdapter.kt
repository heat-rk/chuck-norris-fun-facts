package ru.heatalways.chucknorrisfunfacts.core.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Abstract class, that "fixed" a problem with ListAdapter when you submit
 * lists with the same addresses a few times and recycler view
 * doesn't update the view
 */
abstract class BaseListAdapter<T, VH: RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, VH>(diffUtil) {

    override fun submitList(list: List<T>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        super.submitList(list?.let { ArrayList(it) }, commitCallback)
    }
}