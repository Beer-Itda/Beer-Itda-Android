package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.*
import com.hjiee.domain.entity.request.RequestSelectedAroma
import com.hjiee.domain.entity.request.RequestSelectedStyle
import com.hjiee.domain.repository.ApiServiceConstants.DEFAULT_PAGE_SIZE

interface BeerRepository {
    suspend fun getBeerAward(): Response<Beer>?
    suspend fun getBeerDetail(id: Int): Response<BeerDetail>?
    suspend fun getStyleBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): Response<Beers>?
    suspend fun getAromaBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): Response<Beers>?
    suspend fun getRandomRecommendBeer(page: Int, size: Int = DEFAULT_PAGE_SIZE): Response<Beers>?
    suspend fun getUserInfo(): User
    suspend fun getMyReview(): List<MyReview>
    suspend fun getReview(beerId: Int): List<Review>
    suspend fun getAromaInfo(): Response<List<Aroma>>
    suspend fun getStyleInfo(): Response<List<StyleLargeCategory>>
    suspend fun getMyFavorite(page: Int, size: Int = DEFAULT_PAGE_SIZE): Beers
    suspend fun getLevelGuide(): Response<Level>

    suspend fun postReview(beerId: Int, starScore: Float, content: String)
    suspend fun postFavorite(beerId: Int)
    suspend fun postSelectedAroma(aromaIdList: RequestSelectedAroma)
    suspend fun postSelectedStyle(styleIdList: RequestSelectedStyle)

    suspend fun deleteReview(beerId: Int)
    suspend fun updateReview(beerId: Int, starScore: Float, content: String)

    suspend fun changeNickName(nickName: String)
    suspend fun search(word: String, page: Int, size: Int = DEFAULT_PAGE_SIZE): Beers

//    suspend fun getAppConfig(): AppConfig
//    suspend fun getBeerList(
//        sortType: String?,
//        style: List<String>? = null,
//        aroma: List<String>? = null,
//        cursor: Int?
//    ): Response?
//
//    suspend fun getPopularBeer(): Response?
//    suspend fun getUserInfo(): User?
//    suspend fun postUserInfo(nickName: String?)
//    suspend fun getBeer(id: Int): Response?
//    suspend fun getSearch(name: String, cursor: Int?): Result<Response>?
//    suspend fun getReview(): List<Review>
//    suspend fun postReview(id: Int, rating: Float, review: String?)
//    suspend fun postFavorite(beerId: Int, isFavorite: Boolean)
//    suspend fun getFavorite(): List<Response>

}