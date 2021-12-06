package com.hjiee.data.repository

import com.hjiee.data.api.BeerApi
import com.hjiee.data.mapper.toDomainEntity
import com.hjiee.data.mapper.toDomainEntityAroma
import com.hjiee.data.mapper.toDomainEntityList
import com.hjiee.domain.entity.DomainEntity.Response
import com.hjiee.domain.entity.DomainEntity.Beer
import com.hjiee.domain.entity.DomainEntity.Beers
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject


class BeerRepositoryImpl @Inject constructor(
    private val beerApi: BeerApi
) : BeerRepository {

    override suspend fun getBeers(): Response<Beers>? {
        return beerApi.getBeerList()?.toDomainEntityList()
    }

    override suspend fun getBeerDetail(id: Int): Response<Beer>? {
        return beerApi.getBeer(id)?.toDomainEntity()
    }

    override suspend fun getStyleBeer(): Response<Beers>? {
        return beerApi.getSelectedStyleBeer(1)?.toDomainEntityList()
    }

    override suspend fun getAromaBeer(): Response<Beers>? {
        return beerApi.getSelectedAromaBeer(1)?.toDomainEntityAroma()
    }

    //    override suspend fun getAppConfig(): Result<AppConfig> {
//        return beerApi.getAppConfig()
//    }
//
//    override suspend fun getBeerList(
//        sortType: String?,
//        style: List<String>?,
//        aroma: List<String>?,
//        cursor: Int?
//    ): Response? {
//        return beerApi.getBeerList(
//            sortType,
//            style,
//            aroma,
//            cursor
//        )?.result
//    }
//
//    override suspend fun getBeer(id: Int): Response? {
//        return beerApi.getBeer(id)?.result?.let {
//            it
//        }
//    }
//
//    override suspend fun getPopularBeer(): Response? {
//        return beerApi.getPopularBeer().result
//    }
//
//    override suspend fun getSearch(name: String, cursor: Int?): Result<Response>? =
//        beerApi.getSearchBeer(name, cursor)
//
//    override suspend fun getUserInfo(): User? {
//        return beerApi.getUserInfo()?.result?.let {
//            it
//        }
//    }
//
//    override suspend fun postUserInfo(nickName: String?) {
//        return beerApi.postUserInfo(nickName)
//    }
//
//    override suspend fun postReview(id: Int, rating: Float, review: String?) {
//        return beerApi.postReview(id, rating, review)
//    }
//
//    override suspend fun getReview(): Results<Review> {
//        return beerApi.getReview()
//    }
//
//    override suspend fun getFavorite(): Results<Response> {
//        return beerApi.getFavorite()
//    }
//
//    override suspend fun postFavorite(beerId: Int, isFavorite: Boolean) {
//        return beerApi.postFavorite(beerId, isFavorite)
//    }
}