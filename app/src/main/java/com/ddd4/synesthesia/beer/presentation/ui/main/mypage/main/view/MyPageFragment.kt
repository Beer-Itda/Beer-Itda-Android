package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.view.MyFavoriteActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.myreview.view.MyReviewActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.view.NickNameActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view.SettingActivity
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.util.log.L
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
            includeMyPageLevelGuide.container.setOnClickListener { moveToLevelGuide() }
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
                moveToNickName()
            }
            is MyPageClickEntity.Profile -> {

            }
        }
    }

    /**
     * 닉네임 변경
     */
    private fun moveToNickName() {
        runCatching {
            start<NickNameActivity>(
                intent = NickNameActivity.getIntent(
                    context = requireContext(),
                    nickName = binding.tvName.text.toString()
                )
            )
        }.onFailure {
            L.e(it)
        }
    }

    /**
     * 설정
     */
    private fun moveToSetting() {
        runCatching {
            start<SettingActivity>()
        }.onFailure {
            L.e(it)
        }
    }

    /**
     * 별점과 리뷰
     */
    private fun moveToReview() {
        runCatching {
            start<MyReviewActivity>()
        }.onFailure {
            L.e(it)
        }
    }

    /**
     * 등급 가이드
     */
    private fun moveToLevelGuide() {
        runCatching {

        }.onFailure {
            L.e(it)
        }
    }

    /**
     * 찜한 맥주
     */
    private fun moveToFavorite() {
        runCatching {
            start<MyFavoriteActivity>()
        }.onFailure {
            L.e(it)
        }
    }

}