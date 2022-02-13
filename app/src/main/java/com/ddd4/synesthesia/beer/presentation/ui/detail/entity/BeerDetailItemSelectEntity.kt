package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.hjiee.core.event.entity.ItemClickEntity

sealed class BeerDetailItemSelectEntity : ItemClickEntity() {
    /** 즐겨찾기 */
//    class Favorite(val beer: Beer) : BeerDetailItemSelectEntity()

    /** 리뷰 전체보기 */
    class ReviewAll(val beerId: Int, val reviewCount: Int) : BeerDetailItemSelectEntity()

}