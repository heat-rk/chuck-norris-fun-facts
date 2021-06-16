package ru.heatalways.chucknorrisfunfacts.presentation.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Абстрактный класс, расширяющий возможности класса ListAdapter
 */
abstract class BaseListAdapter<T, VH: RecyclerView.ViewHolder>(diffUtil: DiffUtil.ItemCallback<T>)
    : ListAdapter<T, VH>(diffUtil) {

    /**
     * Устанавливает новый список в адаптер RecyclerView.
     * Используйте данный метод, если вам необходимо установить новый список в
     * RecyclerView со "сбросом состояния" (после вызова данного метода список
     * проскроллится наверх и отобразит новые данные)
     */
    fun newList(list: List<T>) {
        submitList(null)
        submitList(list)
    }
}