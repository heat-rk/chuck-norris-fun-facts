package ru.heatalways.chucknorrisfunfacts.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.heatalways.chucknorrisfunfacts.data.entities.Category
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils.CategoryDiffUtil
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.CategoryViewHolder

class CategoriesAdapter: ListAdapter<Category, CategoryViewHolder>(CategoryDiffUtil()) {
    var onCategoryClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onCategoryClick)
    }
}