package ru.heatalways.chucknorrisfunfacts.presentation.adapters

import android.view.ViewGroup
import ru.heatalways.chucknorrisfunfacts.business.datasource.network.chuck_norris_jokes.Category
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.diff_utils.CategoryDiffUtil
import ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders.CategoryViewHolder
import ru.heatalways.chucknorrisfunfacts.presentation.base.BaseListAdapter

class CategoriesAdapter: BaseListAdapter<Category, CategoryViewHolder>(CategoryDiffUtil()) {
    var onCategoryClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onCategoryClick)
    }
}