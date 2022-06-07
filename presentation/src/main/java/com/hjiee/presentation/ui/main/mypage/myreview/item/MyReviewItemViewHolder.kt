package com.hjiee.presentation.ui.main.mypage.myreview.item

import android.view.View
import com.hjiee.presentation.databinding.ItemMyReviewBinding
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder

class MyReviewItemViewHolder(
    itemView: View
) : BaseBindingViewHolder<MyReviewItemViewModel, ItemMyReviewBinding>(itemView) {

    override fun onBind(viewModel: MyReviewItemViewModel, position: Int) {
        binding?.apply {
            item = viewModel
        }
    }
}