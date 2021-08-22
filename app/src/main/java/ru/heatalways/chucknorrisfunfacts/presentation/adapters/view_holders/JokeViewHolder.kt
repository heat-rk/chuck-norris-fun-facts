package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.ChuckJokeNetwork
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.databinding.ItemJokeHolderViewBinding
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.ImageLoader
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.image_loader.LoadPhotoConfig

class JokeViewHolder private constructor(rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ItemJokeHolderViewBinding.bind(rootView)
    private val appContext = rootView.context.applicationContext
    private val entryPoint get() = EntryPoints.get(appContext, JokeViewHolderEntryPoint::class.java)

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface JokeViewHolderEntryPoint {
        val imageLoader: ImageLoader
    }

    fun bind(joke: ChuckJoke) {
        entryPoint.imageLoader.loadImage(
            config = LoadPhotoConfig(url = joke.iconUrl),
            imageView = binding.iconImageView
        )

        binding.jokeTextView.text = joke.value.getText(itemView.context)
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