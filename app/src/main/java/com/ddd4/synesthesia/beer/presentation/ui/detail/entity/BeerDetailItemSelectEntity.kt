package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class BeerDetailItemSelectEntity : ItemClickEntity() {
    /** 즐겨찾기 */
    class Favorite(val beer: Beer) : BeerDetailItemSelectEntity()

    /** 리뷰 전체보기 */
    object ReviewAll : BeerDetailItemSelectEntity()

    /** 별점 남기기 */
    object StarRate : BeerDetailItemSelectEntity()
}