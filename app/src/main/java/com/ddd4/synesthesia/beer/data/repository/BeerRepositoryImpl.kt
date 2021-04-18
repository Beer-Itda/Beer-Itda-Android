package com.ddd4.synesthesia.beer.data.repository

import com.ddd4.synesthesia.beer.data.model.*
import com.ddd4.synesthesia.beer.data.source.remote.service.BeerApi
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(

    private val beerApi: BeerApi
) : BeerRepository {

    override suspend fun getAppConfig(): Result<AppConfig> {
        return beerApi.getAppConfig()
    }

    override suspend fun getBeerList(
        sortType: String?,
        style: List<String>?,
        aroma: List<String>?,
        cursor: Int?
    ): Response? {
        return beerApi.getBeerList(
            sortType,
            style,
            aroma,
            cursor
        )?.result
    }

    override suspend fun getBeer(id: Int): Response? {
        return beerApi.getBeer(id)?.result?.let {
            it
        }
    }

    override suspend fun getPopularBeer(): Response? {
        return beerApi.getPopularBeer().result
    }

    override suspend fun getSearch(name: String, cursor: Int?): Result<Response>? =
        beerApi.getSearchBeer(name, cursor)

    override suspend fun getUserInfo(): User? {
        return beerApi.getUserInfo()?.result?.let {
            it
        }
    }

    override suspend fun postUserInfo(nickName: String?) {
        return beerApi.postUserInfo(nickName)
    }

    override suspend fun postReview(id: Int, rating: Float, review: String?) {
        return beerApi.postReview(id, rating, review)
    }

    override suspend fun getReview(): Results<Review> {
        return beerApi.getReview()
    }

    override suspend fun getFavorite(): Results<Response> {
        return beerApi.getFavorite()
    }

    override suspend fun postFavorite(beerId: Int, isFavorite: Boolean) {
        return beerApi.postFavorite(beerId, isFavorite)
    }
}