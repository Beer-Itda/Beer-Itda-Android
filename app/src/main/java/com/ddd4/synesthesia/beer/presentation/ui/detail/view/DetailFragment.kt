package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ddd4.synesthesia.beer.HomeNavigationDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Related
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.databinding.FragmentDetailBinding
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.DetailViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()
    private val detailViewModel by viewModels<DetailViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T?) {
                when(item) {
                    // 리뷰
                    is Review -> {

                    }
                    // 향
                    is String -> {

                    }
                    // 관련 맥주
                    is Related -> {
                        findNavController().navigate(HomeNavigationDirections.actionToDetail(item.id))
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = detailViewModel
            aromaAdapter = ItemsApdater(R.layout.layout_aroma, BR.scent ,itemClickListener)
            reviewAdapter = ItemsApdater(R.layout.layout_review, BR.review ,itemClickListener)
            aromaRelatedAdapter = ItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)
            styleRelatedAdapter = ItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)
            randomRelatedAdapter = ItemsApdater(R.layout.layout_beer_card, BR.related, itemClickListener)

            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            tvReviewAll.setOnClickListener {
                findNavController().navigate(HomeNavigationDirections.actionToReviewAll(detailViewModel.response.value?.beer?.reviews?.toTypedArray()))
            }
            rbBeerRate.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_UP -> {
                        StarRatingBottomDialog().run {
                            val bundle = Bundle()
                            bundle.putParcelable("beer",detailViewModel.response.value?.beer)
                            this@run.arguments = bundle
                            showDialog(this@DetailFragment.parentFragmentManager) {
                                detailViewModel.fetchBeer(args.beerId)
                                context?.showToast(getString(R.string.registered_review))
                            }
                        }
                    }
                }
                true
            }
        }

        detailViewModel.fetchBeer(args.beerId)
        initObserver()
    }

    override fun initObserver() {
        super.initObserver()
        detailViewModel.response.observe(viewLifecycleOwner, Observer { result ->
            result.beer?.reviews?.let { binding.reviewAdapter?.updateItems(it) }
            result.beer?.aromas?.let { binding.aromaAdapter?.updateItems(it) }
            result.relatedBeers?.aromaRelated?.let { binding.aromaRelatedAdapter?.updateItems(it) }
            result.relatedBeers?.styleRelated?.let { binding.styleRelatedAdapter?.updateItems(it) }
            result.relatedBeers?.randomlyRelated?.let { binding.randomRelatedAdapter?.updateItems(it) }
        })
    }
}