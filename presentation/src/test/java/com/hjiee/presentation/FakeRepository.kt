package com.hjiee.presentation

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.entity.request.RequestSelectedAroma
import com.hjiee.domain.entity.request.RequestSelectedStyle
import com.hjiee.domain.repository.BeerRepository

open class FakeRepository : BeerRepository {
    override suspend fun postReview(beerId: Int, starScore: Float, content: String) {
    }

    override suspend fun postFavorite(beerId: Int) {
    }

    override suspend fun postSelectedAroma(aromaIdList: RequestSelectedAroma) {
    }

    override suspend fun postSelectedStyle(styleIdList: RequestSelectedStyle) {
    }

    override suspend fun deleteReview(beerId: Int) {
    }

    override suspend fun updateReview(beerId: Int, starScore: Float, content: String) {
    }

    override suspend fun changeNickName(nickName: String) {
    }

    override suspend fun getBeerAward(): DomainEntity.Beer? {
        return null
    }

    override suspend fun getBeerDetail(id: Int): DomainEntity.BeerDetail? {
        return null
    }

    override suspend fun getLevelGuide(): DomainEntity.Level? {
        return null
    }

    override suspend fun getUserInfo(): DomainEntity.User? {
        return null
    }

    override suspend fun getStyleInfo(): List<DomainEntity.StyleLargeCategory> {
        return emptyList()
    }

    override suspend fun getAromaInfo(): List<DomainEntity.Aroma> {
        return emptyList()
    }

    override suspend fun getMyReview(
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.MyReview> {
        return DomainEntity.PageResult()
    }

    override suspend fun getReview(
        beerId: Int,
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Review> {
        return DomainEntity.PageResult()
    }

    override suspend fun getMyFavorite(
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Beer> {
        return DomainEntity.PageResult()
    }

    override suspend fun getStyleBeer(
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Beer> {
        return DomainEntity.PageResult()
    }

    override suspend fun getAromaBeer(
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Beer> {
        return DomainEntity.PageResult()
    }

    override suspend fun getRandomRecommendBeer(
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Beer> {
        return DomainEntity.PageResult()
    }

    override suspend fun search(
        word: String,
        page: Int,
        size: Int
    ): DomainEntity.PageResult<DomainEntity.Beer> {
        return DomainEntity.PageResult()
    }
}