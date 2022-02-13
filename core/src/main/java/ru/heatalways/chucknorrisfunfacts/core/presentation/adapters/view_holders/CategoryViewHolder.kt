package ru.heatalways.chucknorrisfunfacts.core.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.heatalways.chucknorrisfunfacts.core.R
import ru.heatalways.chucknorrisfunfacts.core.databinding.ItemCategoryHolderViewBinding
import ru.heatalways.chucknorrisfunfacts.core.domain.models.Category

class CategoryViewHolder private constructor(
    private val rootView: View
): RecyclerView.ViewHolder(rootView) {

    private val binding = ItemCategoryHolderViewBinding.bind(rootView)

    fun bind(category: Category, onClick: ((Category) -> Unit)? = null) {
        binding.root.text = when (category) {
            Category.Any -> rootView.resources.getString(R.string.random_joke_any_category)
            is Category.Specific -> category.name
        }

        binding.root.setOnClickListener {
            onClick?.invoke(category)
        }
    }

    companion object {
        fun create(parentView: ViewGroup): CategoryViewHolder {
            return CategoryViewHolder(
                LayoutInflater
                    .from(parentView.context)
                    .inflate(R.layout.item_category_holder_view, parentView, false)
            )
        }
    }
}