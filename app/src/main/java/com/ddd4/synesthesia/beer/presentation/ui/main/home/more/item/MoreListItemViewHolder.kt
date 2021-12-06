package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemHome2Binding
import com.ddd4.synesthesia.beer.util.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.main.home.more.view.MoreListHolder

class MoreListItemViewHolder constructor(
    itemView: View
) : MoreListHolder<MoreListItemViewModel, ItemHome2Binding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            MoreListItemViewHolder(parent.createView(R.layout.item_home2))
    }

    override fun onBind(viewModel: MoreListItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}