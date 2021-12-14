package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.view.MyFavoriteActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.view.NickNameActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.review.view.MyReviewActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view.SettingActivity
import com.ddd4.synesthesia.beer.util.ext.start
import dagger.hilt.android.AndroidEntryPoint

/**
 * 마이페이지
 */
@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()

        firstLoaded {
            viewModel.load()
        }
    }

    override fun initBind() {
        binding.run {
            viewModel = this@MyPageFragment.viewModel
            binding.ivSetting.setOnClickListener { moveToSetting() }
            includeMyPageReview.container.setOnClickListener { moveToReview() }
            includeMyPageFavorite.container.setOnClickListener { moveToFavorite() }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is MyPageClickEntity.Modify -> {
                NickNameActivity.start(requireContext(), binding.tvName.text.toString())
            }
        }
    }

    /**
     * 설정
     */
    private fun moveToSetting() {
        start(intent = SettingActivity.getIntent(requireContext()))
    }

    /**
     * 별점과 리뷰
     */
    private fun moveToReview() {
        start(intent = MyReviewActivity.getIntent(requireContext()))
    }

    /**
     * 찜한 맥주
     */
    private fun moveToFavorite() {
        start(intent = MyFavoriteActivity.getIntent(requireContext()))
    }

}