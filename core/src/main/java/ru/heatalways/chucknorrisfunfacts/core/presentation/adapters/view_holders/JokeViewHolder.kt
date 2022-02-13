package ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.heatalways.chucknorrisfunfacts.core.R
import ru.heatalways.chucknorrisfunfacts.core.databinding.ItemJokeHolderViewBinding
import ru.heatalways.chucknorrisfunfacts.core.domain.models.ChuckJoke
import ru.heatalways.chucknorrisfunfacts.common.extensions.loadImage
import ru.heatalways.chucknorrisfunfacts.common.extensions.setVisibleOrInvisible
import ru.heatalways.chucknorrisfunfacts.common.extensions.toCharSequence
import ru.heatalways.chucknorrisfunfacts.common.presentation.utils.LoadPhotoConfig

class JokeViewHolder private constructor(rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ItemJokeHolderViewBinding.bind(rootView)

    fun bind(joke: ChuckJoke) {
        binding.iconImageView.loadImage(LoadPhotoConfig(url = joke.iconUrl))
        binding.jokeTextView.text = joke.value.toCharSequence(itemView.context)
        binding.mainLayout.setVisibleOrInvisible(!joke.isUpdating)
        binding.progressBar.isVisible = joke.isUpdating
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