package com.ddd4.synesthesia.beer.presentation.base.recyclerview2

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.ext.createView

/**
 * viewType 이 1개 인 단순한 listAdapter
 */
abstract class SimpleBindingListAdapter<VM : Any>(
        @LayoutRes val layoutId: Int
) : RecyclerView.Adapter<BaseBindingViewHolder<VM, ViewDataBinding>>(), ItemListProvider<VM> {
    val list: ArrayList<VM> = ArrayList()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BaseBindingViewHolder<VM, ViewDataBinding> {
        return createViewHolder(parent.createView(layoutId))
    }

    abstract fun createViewHolder(view: View): BaseBindingViewHolder<VM, ViewDataBinding>

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

    fun addAll(items: List<VM>) {
        val start = itemCount
        list.addAll(items)
        notifyItemRangeChanged(start, items.size)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }
}
