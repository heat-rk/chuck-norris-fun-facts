package ru.heatalways.chucknorrisfunfacts.presentation.adapters.decorators

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class MarginItemDecoration(
    @DimenRes private val marginRes: Int
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val margin = view.resources.getDimension(marginRes)
        outRect.top = margin.roundToInt()
    }
}