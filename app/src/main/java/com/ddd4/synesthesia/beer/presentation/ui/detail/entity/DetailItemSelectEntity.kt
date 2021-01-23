package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

import com.ddd4.synesthesia.beer.data.model.Beer
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

sealed class DetailItemSelectEntity : ItemClickEntity() {
    /** 즐겨찾기 */
    class Favorite(val beer : Beer) : DetailItemSelectEntity()
    /** 리뷰 전체보기 */
    object ReviewAll : DetailItemSelectEntity()
    /** 별점 남기기 */
    object StarRate : DetailItemSelectEntity()
}