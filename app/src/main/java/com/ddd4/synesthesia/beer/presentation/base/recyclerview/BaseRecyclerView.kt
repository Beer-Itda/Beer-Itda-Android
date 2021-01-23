package com.ddd4.synesthesia.beer.presentation.base.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.util.RecyclerDiffUtil

class BaseRecyclerView {
    abstract class SimpleAdapter<B : ViewDataBinding, T>(
        private val layoutId: Int,
        private val listItem: List<T>,
        private val bindingVariableId: Int?
    ) : RecyclerView.Adapter<ViewHolder<B>>() {

        private var list = listItem

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> =
            object : ViewHolder<B>(layoutId, parent, bindingVariableId) { }

        override fun getItemCount(): Int = list.size
        override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) =
            holder.onBind(list[position])
    }

    abstract class Adapter<ITEM : Any, B : ViewDataBinding>(
        private val layoutId: Int,
        private val bindingVariableId: Int?
    ) : RecyclerView.Adapter<ViewHolder<B>>() {

        var list = listOf<ITEM>()
        private var lastClickTime = 0L

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
            return object : ViewHolder<B>(
                layoutId,
                parent,
                bindingVariableId
            ) { }
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) =
            holder.onBind(list[position])

        fun updateItems(items: List<ITEM>) {
            RecyclerDiffUtil(list, items).apply {
                list = items
                DiffUtil.calculateDiff(this).dispatchUpdatesTo(this@Adapter)
            }
        }
    }

    class LoadingViewHolder<B : ViewDataBinding>(
        private val layoutId: Int,
        private val parent: ViewGroup
    ) : ViewHolder<B>(layoutId,parent,null) {

    }

    abstract class ViewHolder<B : ViewDataBinding?>(
        private val layoutId: Int,
        private val parent: ViewGroup,
        private val bindingVariableId: Int?
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {

        val binding = DataBindingUtil.bind<B>(itemView)

        fun onBind(item: Any?) {
            bindingVariableId?.let {
                binding?.setVariable(it, item)
                binding?.executePendingBindings()
            }
        }
    }
}

enum class BaseViewType {
    ITEM,
    LOADING
}