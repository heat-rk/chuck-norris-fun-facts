package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.data.entities.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.databinding.ItemJokeHolderViewBinding
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.ImageLoaderManager
import ru.heatalways.chucknorrisfunfacts.domain.managers.glide.LoadPhotoConfig
import ru.heatalways.chucknorrisfunfacts.extensions.entryPointOf

class JokeViewHolder private constructor(rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ItemJokeHolderViewBinding.bind(rootView)

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface JokeViewHolderEntryPoint {
        fun getImageLoader(): ImageLoaderManager
    }

    fun bind(joke: ChuckJoke) {
        entryPointOf(JokeViewHolderEntryPoint::class.java).getImageLoader().loadImage(
            config = LoadPhotoConfig(url = joke.iconUrl),
            imageView = binding.iconImageView
        )

        binding.jokeTextView.text = joke.value
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