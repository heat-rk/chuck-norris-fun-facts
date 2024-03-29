package ru.heatalways.chucknorrisfunfacts.core.presentation.adapters

import android.view.ViewGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.diff_utils.CategoryDiffUtil
import ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.view_holders.CategoryViewHolder
import ru.heatalways.chucknorrisfunfacts.core.presentation.base.BaseListAdapter

class CategoriesAdapter: BaseListAdapter<Category, CategoryViewHolder>(CategoryDiffUtil()) {
    var onCategoryClick: ((Category) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onCategoryClick)
    }

    fun categoryClicks(): Flow<Category> = channelFlow {
        onCategoryClick = { if (isActive) { trySend(it) } }
        awaitClose { onCategoryClick = {} }
    }
}