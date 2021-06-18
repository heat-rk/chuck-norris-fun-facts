package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.heatalways.chucknorrisfunfacts.App
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.databinding.ItemJokeHolderViewBinding
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.LoadPhotoConfig

class JokeViewHolder private constructor(rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ItemJokeHolderViewBinding.bind(rootView)

    fun bind(joke: ChuckJoke) {
        App.appComponent.getImageLoaderManager().loadImage(
            config = LoadPhotoConfig(url = joke.iconUrl),
            imageView = binding.iconImageView
        )

        binding.jokeTextView.text = joke.value
    }

    interface Listener {
        fun onJokeClicked(joke: ChuckJoke)
    }

    companion object {
        fun create(parentView: ViewGroup): JokeViewHolder {
            return JokeViewHolder(
                LayoutInflater
                    .from(parentView.context)
                    .inflate(R.layout.item_joke_holder_view, parentView, false)
            )
        }
    }
}