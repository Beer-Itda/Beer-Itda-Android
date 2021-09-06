package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.view

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentMyReviewBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.viewmodel.MyReviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>(R.layout.fragment_my_review) {

    private val viewModel by viewModels<MyReviewViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            reviewAdatper = ItemsAdapter(R.layout.item_my_review, BR.review)
            srvReview.setOnRefreshListener {
                viewModel.review()
            }
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        initObserver()
    }

    override fun initObserver() {
        super.initObserver()
        viewModel.myReviews.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                binding.reviewAdatper?.updateItems(list)
                binding.srvReview.isRefreshing = false
            }
        })
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectItem -> {

            }
        }
    }
}