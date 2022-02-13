package ru.heatalways.chucknorrisfunfacts.core.presentation.adapters

import android.view.ViewGroup
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.diff_utils.ChuckJokeDiffUtil
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.view_holders.JokeViewHolder
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.BaseListAdapter

class JokesAdapter: BaseListAdapter<ChuckJoke, JokeViewHolder>(ChuckJokeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}