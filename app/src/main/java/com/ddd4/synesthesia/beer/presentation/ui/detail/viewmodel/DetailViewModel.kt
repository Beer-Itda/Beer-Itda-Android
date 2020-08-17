package com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel

import androidx.databinding.library.baseAdapters.BR
import androidx.hilt.lifecycle.ViewModelInject
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.Review
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener

class DetailViewModel @ViewModelInject constructor(

) : BaseViewModel() {

    val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
            }
        }
    }
    val scentAdatper : BaseItemsApdater by lazy { BaseItemsApdater(R.layout.layout_scent, BR.scent ,itemClickListener) }
    val reviewAdapter : BaseItemsApdater by lazy { BaseItemsApdater(R.layout.layout_review, BR.review ,itemClickListener) }

    init {
        setDummyItems()
    }

    fun setDummyItems() {
        scentAdatper.updateItems(listOf("향1","향2","향3","향4","향5","향6","향7","향8","향9","향10","향11","향12"))
        val list = arrayListOf<Review>()
        for (i in 0..30) {
            list.add(Review("공감각",5.0f,"2020. 08. 17","아 맥주.. 제 인생 맥주입니다 ㅠㅠ 너무 맛있어요ㅠㅠㅠ 저는 과일향 나는, 가볍게 마실 수 있는 맥주를 선호하는데, 그런저에게 딱인 것 같습니다! 상큼한 향이 너무 좋았어요"))
        }
        reviewAdapter.updateItems(list)
    }
}