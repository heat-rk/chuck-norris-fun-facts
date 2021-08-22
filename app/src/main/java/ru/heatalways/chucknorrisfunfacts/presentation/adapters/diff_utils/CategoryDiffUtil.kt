package ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils

import androidx.recyclerview.widget.DiffUtil
import ru.heatalways.chucknorrisfunfacts.business.domain.repositories.chuck_norris_jokes.Category

class CategoryDiffUtil: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}