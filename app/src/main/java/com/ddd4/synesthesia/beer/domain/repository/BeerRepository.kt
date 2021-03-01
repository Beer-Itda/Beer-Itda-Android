package com.ddd4.synesthesia.beer.domain.repository

import com.ddd4.synesthesia.beer.data.model.*
import com.ddd4.synesthesia.beer.presentation.ui.common.filter.BeerFilter

interface BeerRepository {
    suspend fun getAppConfig(): Result<AppConfig>
    suspend fun getBeerList(sortType: String?, filter: BeerFilter?, cursor : Int?): Response?
    suspend fun getUserInfo(): User?
    suspend fun postUserInfo(nickName: String?)
    suspend fun getBeer(id: Int): Response?
    suspend fun getSearch(name : String, cursor : Int?) : Result<Response>?
    suspend fun getReview() : Results<Review>
    suspend fun postReview(id: Int, rating: Float, review: String?)
    suspend fun postFavorite(beerId : Int, isFavorite : Boolean)
    suspend fun getFavorite() : Results<Response>

}