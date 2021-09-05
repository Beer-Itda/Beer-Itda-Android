package com.ddd4.synesthesia.beer.presentation.ui.common.loading

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutLoadingBinding
import com.ddd4.synesthesia.beer.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.home.more.view.MoreListHolder

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