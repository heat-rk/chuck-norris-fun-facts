package ru.heatalways.chucknorrisfunfacts.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils.ChuckJokeDiffUtil
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeViewHolder

class JokesAdapter: ListAdapter<ChuckJoke, JokeViewHolder>(ChuckJokeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}