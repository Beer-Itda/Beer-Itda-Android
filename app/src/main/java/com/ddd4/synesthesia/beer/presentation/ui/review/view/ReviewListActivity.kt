package com.ddd4.synesthesia.beer.presentation.ui.review.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityReviewAllBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.hjiee.domain.entity.DomainEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewListActivity : BaseActivity<ActivityReviewAllBinding>(R.layout.activity_review_all) {

    private val reviews by lazy { intent.getParcelableArrayExtra(KEY_REVIEW)?.toList() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
    }

    override fun initBind() {
        binding.apply {
            reviewAdatper = ItemsAdapter(R.layout.layout_review, BR.review)
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            reviews?.let { reviewAdatper?.updateItems(it) }
        }
    }

    companion object {
        const val KEY_REVIEW = "reviews"

        fun getIntent(context: Context, review: Array<DomainEntity.Review>?): Intent {
            return Intent(context, ReviewListActivity::class.java).apply {
                putExtra(KEY_REVIEW, review)
            }
        }
    }
}