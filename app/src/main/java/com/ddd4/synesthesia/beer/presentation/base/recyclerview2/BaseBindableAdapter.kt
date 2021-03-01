package com.ddd4.synesthesia.beer.presentation.base.recyclerview2

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindableAdapter<VT : Enum<VT>, VM : IViewTypeGetter<VT>> :
    RecyclerView.Adapter<BaseBindingViewHolder<VM, ViewDataBinding>>(), ItemListProvider<VM> {

    companion object {
        const val INVALID_POSITION: Int = -1
    }

    val list: ArrayList<VM> = ArrayList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemList() = list

    override fun onBindViewHolder(
        viewHolder: BaseBindingViewHolder<VM, ViewDataBinding>,
        position: Int
    ) {
        list.getOrNull(position)?.let {
            viewHolder.onBind(it, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewTypeOrdinal()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun removeItem(item: VM) {
        list.indexOf(item).takeIf { it > INVALID_POSITION }?.let {
            list.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    fun setData(item: VM) {
        var addPosition = getInsertPosition(item.getViewTypeOrdinal())

        if (addPosition == INVALID_POSITION) { // add end position
            list.add(item)
            addPosition = list.size - 1
        } else { // insert
            list.add(addPosition, item)
        }
        notifyItemRangeInserted(addPosition, 1)
    }

    fun addAll(items: List<VM>) {
        if (items.isEmpty()) return

        /** list가 비어 있으면 그냥 add */
        if (list.isEmpty()) {
            list.addAll(items)
            notifyItemRangeInserted(0, items.size)
            return
        }

        /** list가 notEmpty 면 위치를 찾아 중간 삽입을 시도 한다 */
        val positionStart = getInsertPosition(items[0].getViewTypeOrdinal())

        if (positionStart > INVALID_POSITION) { // insert
            list.addAll(positionStart, items)
            notifyItemRangeInserted(positionStart, items.size)
        } else { // add end position
            val count = itemCount
            list.addAll(items)
            notifyItemRangeInserted(count, items.size)
        }
    }

    fun removeViewType(viewTypeName: String) {
        val removeIndexList = list.withIndex()
            .filter {
                it.value.getViewTypeName() == viewTypeName
            }.map {
                it.index
            }.asReversed()

        removeItems(removeIndexList)
    }

    fun removeWithoutViewType(viewTypeName: String) {
        val removeIndexList = list.withIndex()
            .filter {
                it.value.getViewTypeName() != viewTypeName
            }.map {
                it.index
            }.asReversed()

        removeItems(removeIndexList)
    }

    fun removeViewType(viewType: VT) {
        val removeIndexList = list.withIndex()
            .filter {
                it.value.getViewType() == viewType
            }.map {
                it.index
            }.asReversed()

        removeItems(removeIndexList)
    }

    private fun removeItems(removeIndexList: List<Int>) {
        for (idx in removeIndexList) {
            list.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }

    /** 해당 뷰 타입이 들어갈 위치를 찾는다. view 가 중간에 삽입되는걸 원치 않으면 항상 INVALID_POSITION 을 리턴하도록 override 시킨다. */
    protected open fun getInsertPosition(viewTypeOrdinal: Int): Int {
        return list.indexOfFirst { it.getViewTypeOrdinal() > viewTypeOrdinal }
    }

    fun getItem(index: Int): VM? = list.getOrNull(index)
}
