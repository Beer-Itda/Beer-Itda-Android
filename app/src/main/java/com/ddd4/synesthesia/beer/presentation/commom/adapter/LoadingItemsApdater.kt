package com.ddd4.synesthesia.beer.presentation.commom.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.recyclerview.BaseRecyclerView
import com.ddd4.synesthesia.beer.presentation.base.recyclerview.BaseViewType

class LoadingItemsApdater(
    private val layoutId: Int,
    private val bindingVariableId: Int? = -1
) : BaseRecyclerView.Adapter<Any, ViewDataBinding>(
    layoutId = layoutId,
    bindingVariableId = bindingVariableId
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerView.ViewHolder<ViewDataBinding> {
        return when (viewType) {
            BaseViewType.LOADING.ordinal -> BaseRecyclerView.LoadingViewHolder(
                R.layout.layout_loading,
                parent
            )
            else -> super.onCreateViewHolder(parent, viewType)
        }
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

    override fun getItemViewType(position: Int): Int {
        return if ((list[position] as Beer).id == -1) {
            BaseViewType.LOADING.ordinal
        } else {
            BaseViewType.ITEM.ordinal
        }
    }
}