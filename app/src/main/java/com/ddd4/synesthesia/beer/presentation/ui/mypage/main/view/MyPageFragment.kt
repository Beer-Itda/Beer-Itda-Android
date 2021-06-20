package com.ddd4.synesthesia.beer.presentation.ui.mypage.main.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.HomeNavigationDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.main.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.presentation.ui.mypage.nickname.view.NickNameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.run {
            vm = viewModel
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
        findNavController().navigate(HomeNavigationDirections.actionToSetting())
    }

    /**
     * 별점과 리뷰
     */
    private fun moveToReview() {
        findNavController().navigate(HomeNavigationDirections.actionToMyReview())
    }

    /**
     * 찜한 맥주
     */
    private fun moveToFavorite() {
        findNavController().navigate(HomeNavigationDirections.actionToMyFavorite())
    }

}