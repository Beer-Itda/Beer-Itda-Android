package com.hjiee.presentation.ui.common.loading

import android.view.View
import android.view.ViewGroup
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.LayoutLoadingBinding
import com.hjiee.presentation.ui.main.home.more.view.MoreListHolder
import com.hjiee.presentation.util.ext.createView

class LoadingViewHolder<VM : Any> constructor(
    itemView: View
) : MoreListHolder<VM, LayoutLoadingBinding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            LoadingViewHolder<Any>(parent.createView(R.layout.layout_loading))
    }

    override fun onBind(viewModel: VM, position: Int) {
        binding?.run {
        }
    }
}