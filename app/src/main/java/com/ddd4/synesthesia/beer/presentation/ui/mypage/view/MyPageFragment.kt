package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.util.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_global_toolbar.view.*

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding> (R.layout.fragment_my_page){

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
            inclideToolbar.toolbar.setNavigationOnClickListener {
                userAdapter = BaseItemsApdater(R.layout.layout_review, BR.review ,itemClickListener)
                parentFragmentManager.popBackStack()
            }
        }
    }
}