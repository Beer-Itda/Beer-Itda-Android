package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.view

import android.view.View
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.BaseBindingViewHolder
import com.ddd4.synesthesia.beer.presentation.base.recyclerview2.SimpleBindingListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.item.MyReviewItemViewModel

class MyReviewAdapter : SimpleBindingListAdapter<MyReviewItemViewModel>(R.layout.item_my_review) {

    override fun createViewHolder(view: View): BaseBindingViewHolder<MyReviewItemViewModel, ViewDataBinding> {
        return MyReviewItemViewHolder(view) as BaseBindingViewHolder<MyReviewItemViewModel, ViewDataBinding>
    }
}
