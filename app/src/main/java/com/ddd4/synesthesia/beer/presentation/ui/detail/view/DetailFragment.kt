package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.databinding.FragmentDetailBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.DetailViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_global_toolbar.view.*

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(R.layout.fragment_detail) {
    private val loginViewModel by viewModels<LoginViewModel>()
    private val detailViewModel by viewModels<DetailViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when(item) {
                    // 리뷰
                    is Review -> {

                    }
                    // 향
                    is String -> {

                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = detailViewModel
            aromaAdapter = BaseItemsApdater(R.layout.layout_aroma, BR.scent ,itemClickListener)
            reviewAdapter = BaseItemsApdater(R.layout.layout_review, BR.review ,itemClickListener)
            btnLogout.setOnClickListener {
                loginViewModel.logout()
            }
            inclideToolbar.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        initObserving()
    }

    override fun initObserving() {
        super.initObserving()
        detailViewModel.beer.observe(viewLifecycleOwner, Observer {
            it.reviews?.let { reviews ->
                binding.reviewAdapter?.updateItems(reviews)
            }
            it.aromas.let { aromas ->
                binding.aromaAdapter?.updateItems(aromas)
            }
        })
        // 임시 로그아웃 처리를 위한 코드
        loginViewModel.isLogoutSuccess.observe(viewLifecycleOwner, Observer {
            if(it) {
                preference.clear()
                start<LoginActivity>(true, bundleOf(Pair(getString(R.string.is_show_snackbar),getString(R.string.success_logout))))
            }
        })
    }
}