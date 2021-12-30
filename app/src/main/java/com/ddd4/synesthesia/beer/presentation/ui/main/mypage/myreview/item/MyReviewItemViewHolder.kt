package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item

import android.view.View
import com.ddd4.synesthesia.beer.databinding.ItemMyReviewBinding
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder

class MyReviewItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<MyReviewItemViewModel, ItemMyReviewBinding>(itemView) {

    override fun onBind(viewModel: MyReviewItemViewModel, position: Int) {
        binding?.apply {
            item = viewModel
        }
    }
}