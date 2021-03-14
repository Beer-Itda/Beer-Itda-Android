package com.ddd4.synesthesia.beer.presentation.ui.like.item

import android.view.View
import android.view.ViewGroup
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ItemHome2Binding
import com.ddd4.synesthesia.beer.ext.createView
import com.ddd4.synesthesia.beer.presentation.ui.like.view.HomeLikeHolder

class HomeLikeItemViewHolder constructor(
    itemView: View
) : HomeLikeHolder<HomeLikeListItemViewModel, ItemHome2Binding>(itemView) {

    companion object {
        fun newInstance(parent: ViewGroup) =
            HomeLikeItemViewHolder(parent.createView(R.layout.item_home2))
    }

    override fun onBind(viewModel: HomeLikeListItemViewModel, position: Int) {
        binding?.run {
            beer = viewModel.beer
            executePendingBindings()
        }
    }
}