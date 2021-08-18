package ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils

import androidx.recyclerview.widget.DiffUtil
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJoke

class ChuckJokeDiffUtil: DiffUtil.ItemCallback<ChuckJoke>() {
    override fun areItemsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChuckJoke, newItem: ChuckJoke): Boolean {
        return oldItem == newItem
    }
}