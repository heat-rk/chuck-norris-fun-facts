package ru.heatalways.chucknorrisfunfacts.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils.ChuckJokeDiffUtil
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.JokeViewHolder

class JokesAdapter: ListAdapter<ChuckJoke, JokeViewHolder>(ChuckJokeDiffUtil()) {
    private var jokes = mutableListOf<ChuckJoke>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(jokes[position])
    }

    override fun getItemCount() = jokes.size

    fun setJokes(jokes: List<ChuckJoke>) {
        this.jokes.clear()
        this.jokes.addAll(jokes)
        submitList(jokes)
    }
}