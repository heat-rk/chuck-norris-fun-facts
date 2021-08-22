package ru.heatalways.chucknorrisfunfacts.presentation.adapters

import android.view.ViewGroup
import ru.heatalways.chucknorrisfunfacts.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils.ChuckJokeDiffUtil
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeViewHolder
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseListAdapter

class JokesAdapter: BaseListAdapter<ChuckJoke, JokeViewHolder>(ChuckJokeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}