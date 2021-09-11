package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityDetailBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.RelatedClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.DetailItemSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.DetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.review.view.ReviewListActivity
import com.ddd4.synesthesia.beer.util.KeyStringConst.Companion.KEY_BEER_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        detailViewModel.load()
    }

    override fun initBind() {
        binding.apply {
            vm = detailViewModel
            aromaAdapter = ItemsAdapter(R.layout.layout_aroma, BR.scent)
            reviewAdapter = ItemsAdapter(R.layout.layout_review, BR.review)
            aromaRelatedAdapter = ItemsAdapter(R.layout.layout_beer_card, BR.related)
            styleRelatedAdapter = ItemsAdapter(R.layout.layout_beer_card, BR.related)
            randomRelatedAdapter = ItemsAdapter(R.layout.layout_beer_card, BR.related)

            includeToolbar.toolbar.setNavigationOnClickListener {
                finish()
            }

            rbBeerRate.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        showStarRatingView()
                    }
                }
                true
            }
        }
    }

    override fun initObserver() {
        detailViewModel.relatedBeers.observe(this@DetailActivity, Observer { result ->
            result?.aromaRelated?.let { binding.aromaRelatedAdapter?.updateItems(it) }
            result?.styleRelated?.let { binding.styleRelatedAdapter?.updateItems(it) }
            result?.randomlyRelated?.let { binding.randomRelatedAdapter?.updateItems(it) }
        })
        detailViewModel.beer.observe(this@DetailActivity, Observer { result ->
            result?.reviews?.let { binding.reviewAdapter?.updateItems(it) }
            result?.aromas?.let { binding.aromaAdapter?.updateItems(it) }
        })
        observeHandledEvent(detailViewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is RelatedClickEntity.SelectItem -> {
                start(intent = getIntent(this, entity.beer.id))
            }
            is DetailItemSelectEntity.ReviewAll -> {
                start(
                    intent = ReviewListActivity.getIntent(
                        this,
                        detailViewModel.beer.value?.reviews?.toTypedArray()
                    )
                )
            }
            is DetailItemSelectEntity.StarRate -> {
                showStarRatingView()
            }
        }
    }

    private fun showStarRatingView() {
        StarRatingBottomDialog().run {
            val bundle = Bundle()
            bundle.putParcelable("beer", detailViewModel.beer.value)
            this@run.arguments = bundle
            showDialog(this@DetailActivity.supportFragmentManager) {
                detailViewModel.load()
                context?.showToast(getString(R.string.registered_review))
            }
        }
    }

    companion object {
        const val REQ_CODE_DETAIL = 10

        fun getIntent(
            context: Context,
            beerId: Int
        ): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_BEER_ID, beerId)
            }
        }
    }

}