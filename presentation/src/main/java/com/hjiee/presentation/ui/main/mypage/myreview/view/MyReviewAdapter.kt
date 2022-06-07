package com.hjiee.presentation.ui.main.mypage.myreview.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.R
import com.hjiee.presentation.base.recyclerview2.BaseBindingViewHolder
import com.hjiee.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.hjiee.presentation.ui.main.mypage.myreview.item.MyReviewItemViewHolder
import com.hjiee.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel

class MyReviewAdapter : SimpleBindingListAdapter<MyReviewItemViewModel>(R.layout.item_my_review) {

    override fun createViewHolder(view: View): BaseBindingViewHolder<MyReviewItemViewModel, ViewDataBinding> {
        return MyReviewItemViewHolder(view) as BaseBindingViewHolder<MyReviewItemViewModel, ViewDataBinding>
    }
}
