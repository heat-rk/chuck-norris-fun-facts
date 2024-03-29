package ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.diff_utils

import androidx.recyclerview.widget.DiffUtil
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke

class ChuckJokeDiffUtil: DiffUtil.ItemCallback<ChuckJoke>() {
    override fun areItemsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem.id == newItem.id && oldItem.actualId == newItem.actualId
    }

    override fun areContentsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem == newItem
    }
}