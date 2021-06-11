package ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils

import androidx.recyclerview.widget.DiffUtil

class StringDiffUtil: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}