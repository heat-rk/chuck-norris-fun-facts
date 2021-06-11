package ru.heatalways.chucknorrisfunfacts.presentation.adapters.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.heatalways.chucknorrisfunfacts.App
import ru.heatalways.chucknorrisfunfacts.R
import ru.heatalways.chucknorrisfunfacts.databinding.ItemCategoryHolderViewBinding

class CategoryViewHolder private constructor(rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ItemCategoryHolderViewBinding.bind(rootView)

    fun bind(category: String?, onClick: ((String?) -> Unit)? = null) {
        binding.root.text = category ?:
            App.instance.resources.getString(R.string.random_joke_any_category)

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