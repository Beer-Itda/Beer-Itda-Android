package com.hjiee.domain.repository

import com.hjiee.domain.entity.DomainEntity.*

interface BeerRepository {
    suspend fun getBeerAward(): Response<Beer>?
    suspend fun getBeers(): Response<Beers>?
    suspend fun getBeerDetail(id: Int): Response<BeerDetail>?
    suspend fun getStyleBeer(): Response<Beers>?
    suspend fun getAromaBeer(): Response<Beers>?
    suspend fun getUserInfo(): User
    suspend fun getMyReview(): List<Review>

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