package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.*
import com.hjiee.domain.entity.request.RequestSelectedAroma
import com.hjiee.domain.entity.request.RequestSelectedStyle
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_PAGE_SIZE

interface BeerRepository {
    suspend fun postReview(beerId: Int, starScore: Float, content: String)
    suspend fun postFavorite(beerId: Int)
    suspend fun postSelectedAroma(aromaIdList: RequestSelectedAroma)
    suspend fun postSelectedStyle(styleIdList: RequestSelectedStyle)
    suspend fun deleteReview(beerId: Int)
    suspend fun updateReview(beerId: Int, starScore: Float, content: String)
    suspend fun changeNickName(nickName: String)

    /** multiple object */
    suspend fun getBeerAward(): Beer?
    suspend fun getBeerDetail(id: Int): BeerDetail?
    suspend fun getLevelGuide(): Level?
    suspend fun getUserInfo(): User?

    /** single list */
    suspend fun getStyleInfo(): List<StyleLargeCategory>
    suspend fun getAromaInfo(): List<Aroma>

    /** pagination */
    suspend fun getMyReview(page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<MyReview>
    suspend fun getReview(beerId: Int, page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Review>
    suspend fun getMyFavorite(page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Beer>
    suspend fun getStyleBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Beer>
    suspend fun getAromaBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Beer>
    suspend fun getRandomRecommendBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Beer>
    suspend fun search(word: String, page: Int, size: Int = DEFAULT_PAGE_SIZE): PageResult<Beer>
}
