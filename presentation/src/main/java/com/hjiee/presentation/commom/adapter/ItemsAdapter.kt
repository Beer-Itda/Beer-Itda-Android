package com.hjiee.presentation.commom.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.base.recyclerview.BaseRecyclerView

class ItemsAdapter(
    private val layoutId: Int,
    private val bindingVariableId: Int? = -1
) : BaseRecyclerView.Adapter<Any, ViewDataBinding>(
    layoutId = layoutId,
    bindingVariableId = bindingVariableId
) {

    private val items = mutableListOf<Any>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerView.ViewHolder<ViewDataBinding> {
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerView.ViewHolder<ViewDataBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
    }

    fun addItems(items: List<Any>) {

    }
}