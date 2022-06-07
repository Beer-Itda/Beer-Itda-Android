package com.hjiee.presentation.base.recyclerview2

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingViewHolder<VM : Any, B : ViewDataBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val binding: B? = DataBindingUtil.bind(itemView)

    abstract fun onBind(viewModel: VM, position: Int)
}