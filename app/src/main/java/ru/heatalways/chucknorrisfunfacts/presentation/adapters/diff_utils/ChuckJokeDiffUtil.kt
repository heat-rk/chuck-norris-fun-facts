package ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils

import androidx.recyclerview.widget.DiffUtil
import ru.heatalways.chucknorrisfunfacts.domain.models.ChuckJoke

class ChuckJokeDiffUtil: DiffUtil.ItemCallback<ChuckJoke>() {
    override fun areItemsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem == newItem
    }
}