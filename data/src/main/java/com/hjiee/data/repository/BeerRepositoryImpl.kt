package com.hjiee.data.repository

import com.hjiee.data.api.BeerApi
import com.hjiee.data.mapper.*
import com.hjiee.domain.entity.DomainEntity.*
import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject


class BeerRepositoryImpl @Inject constructor(
    private val beerApi: BeerApi
) : BeerRepository {

    override suspend fun getUserInfo(): User {
        return beerApi.getUserInfo()?.data.toUserInfo()
    }

    override suspend fun getBeers(): Response<Beers>? {
        return beerApi.getBeerList()?.toBeerList()
    }

    override suspend fun getBeerDetail(id: Int): Response<BeerDetail>? {
        return beerApi.getBeerDetail(id)?.toBeerDetail()
    }

    override suspend fun getStyleBeer(): Response<Beers>? {
        return beerApi.getSelectedStyleBeer()?.toBeerList()
    }

    override suspend fun getAromaBeer(): Response<Beers>? {
        return beerApi.getSelectedAromaBeer()?.toSelectedBeerFromAroma()
    }

    override suspend fun getRandomRecommendBeer(): Response<Beers> {
        return beerApi.getRandomRecommendBeer().toBeerList()
    }

    override suspend fun getMyReview(): List<MyReview> {
        return beerApi.getMyReview().toMyReviewList()
    }

    override suspend fun getAromaInfo(): Response<List<Aroma>> {
        return beerApi.getAromaInfo().toAromaList()
    }

    override suspend fun getStyleInfo(): Response<List<StyleLargeCategory>> {
        return beerApi.getStyleInfo().toStyleLargeCategory()
    }

    override suspend fun getMyFavorite(): Response<Beers> {
        return beerApi.getMyFavorite().toBeerList()
    }

    override suspend fun getLevelGuide(): Response<Level> {
        return beerApi.getLevelGuide().toLevelGuide()
    }

    override suspend fun postReview(
        beerId: Int,
        starScore: Float,
        content: String
    ) {
        beerApi.postReview(
            beerId = beerId,
            starScore = starScore,
            content = content
        )
    }

    override suspend fun postFavorite(beerId: Int) {
        beerApi.postFavorite(beerId = beerId)
    }

    override suspend fun getBeerAward(): Response<Beer>? {
        return beerApi.getBeerAward()?.toAwardBeer()
    }

    override suspend fun changeNickName(nickName: String) {
        beerApi.changeNickName(nickName)
    }

    override suspend fun search(word: String, page: Int, size: Int): Beers {
        return beerApi.search(
            word = word,
            page = page,
            size = size
        ).toBeerList()
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