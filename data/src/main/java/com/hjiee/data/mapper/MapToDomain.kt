package com.hjiee.data.mapper

import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import com.hjiee.data.response.common.NetworkResponse
import com.hjiee.data.response.v2.*
import com.hjiee.domain.entity.DomainEntity

fun NetworkResponse<BeerDetailResponse>?.toBeerDetail(): DomainEntity.Response<DomainEntity.BeerDetail> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.BeerDetail(
            beer = this?.data?.beerDetail?.toBeer(),
            relatedAromaBeer = this?.data?.sameAromaBeers?.toBeer().orEmpty(),
            relatedStyleBeer = this?.data?.sameStyleBeers?.toBeer().orEmpty()
        )
    )
}

fun NetworkResponse<BeerResponse>?.toBeer(): DomainEntity.Response<DomainEntity.Beer> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.Beer(
            id = this?.data?.id.orZero(),
            abv = this?.data?.abv.orZero(),
            nameForKorean = this?.data?.nameForKorean.orEmpty(),
            nameForEnglish = this?.data?.nameForEnglish.orEmpty(),
            thumbnailImage = this?.data?.thumbnailImage.orEmpty(),
            starAvg = this?.data?.starAvg.orZero(),
            brewery = this?.data?.brewery.orEmpty(),
            country = this?.data?.country.orEmpty(),
            style = this?.data?.style.orEmpty(),
            isFavorite = this?.data?.heart.orFalse()
        )
    )
}


fun NetworkResponse<BeersResponse>?.toBeerList(): DomainEntity.Response<DomainEntity.Beers> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.Beers(
            beers = this?.data?.beers?.map {
                DomainEntity.Beer(
                    id = it.id.orZero(),
                    abv = it.abv.orZero(),
                    nameForKorean = it.nameForKorean.orEmpty(),
                    nameForEnglish = it.nameForEnglish.orEmpty(),
                    thumbnailImage = it.thumbnailImage.orEmpty(),
                    starAvg = it.starAvg.orZero(),
                    brewery = it.brewery.orEmpty(),
                    country = it.country.orEmpty(),
                    style = it.style.orEmpty(),
                    isFavorite = it.heart.orFalse()
                )
            }.orEmpty()
        )
    )
}

fun NetworkResponse<BeerListResponse>?.toBeerListWithPagination(): DomainEntity.Response<DomainEntity.Beers> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.Beers(
            beers = this?.data?.beers?.map {
                DomainEntity.Beer(
                    id = it.id.orZero(),
                    abv = it.abv.orZero(),
                    nameForKorean = it.nameForKorean.orEmpty(),
                    nameForEnglish = it.nameForEnglish.orEmpty(),
                    thumbnailImage = it.thumbnailImage.orEmpty(),
                    starAvg = it.starAvg.orZero(),
                    brewery = it.brewery.orEmpty(),
                    country = it.country.orEmpty(),
                    style = it.style.orEmpty(),
                    isFavorite = it.heart.orFalse()
                )
            }.orEmpty()
        )
    )
}

fun NetworkResponse<AwardResponse>?.toAwardBeer(): DomainEntity.Response<DomainEntity.Beer> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.Beer(
            id = this?.data?.beer?.id.orZero(),
            abv = this?.data?.beer?.abv.orZero(),
            nameForKorean = this?.data?.beer?.nameForKorean.orEmpty(),
            nameForEnglish = this?.data?.beer?.nameForEnglish.orEmpty(),
            thumbnailImage = this?.data?.beer?.thumbnailImage.orEmpty(),
            starAvg = this?.data?.beer?.starAvg.orZero(),
            brewery = this?.data?.beer?.brewery.orEmpty(),
            country = this?.data?.beer?.country.orEmpty(),
            style = this?.data?.beer?.style.orEmpty(),
            isFavorite = this?.data?.beer?.heart.orFalse()
        )
    )
}


fun BeerResponse?.toBeer(): DomainEntity.Beer {
    return DomainEntity.Beer(
        id = this?.id.orZero(),
        nameForKorean = this?.nameForKorean.orEmpty(),
        abv = this?.abv.orZero(),
        nameForEnglish = this?.nameForEnglish.orEmpty(),
        thumbnailImage = this?.thumbnailImage.orEmpty(),
        starAvg = this?.starAvg.orZero(),
        brewery = this?.brewery.orEmpty(),
        country = this?.country.orEmpty(),
        style = this?.style.orEmpty(),
        isFavorite = this?.heart.orFalse()
    )
}

fun List<BeerResponse>?.toBeer(): List<DomainEntity.Beer> {
    return this?.map {
        DomainEntity.Beer(
            id = it.id.orZero(),
            nameForKorean = it.nameForKorean.orEmpty(),
            abv = it.abv.orZero(),
            nameForEnglish = it.nameForEnglish.orEmpty(),
            thumbnailImage = it.thumbnailImage.orEmpty(),
            starAvg = it.starAvg.orZero(),
            style = it.style.orEmpty(),
            brewery = it.brewery.orEmpty(),
            country = it.country.orEmpty(),
            isFavorite = it.heart.orFalse(),
        )
    }.orEmpty()
}

fun LoginResponse?.toTokenInfo(): DomainEntity.TokenInfo {
    return DomainEntity.TokenInfo(
        accessToken = this?.accessToken.orEmpty(),
        refreshToken = this?.refreshToken.orEmpty()
    )
}

fun UserResponse?.toUserInfo(): DomainEntity.User {
    return DomainEntity.User(
        id = this?.id.orZero(),
        email = this?.email.orEmpty(),
        nickName = this?.nickname.orEmpty(),
        reviewCount = this?.reviewCount.orZero(),
        levelId = this?.levelId.orZero(),
        profileImage = "",
    )
}

fun List<ReviewResponse>?.toReviewList(): List<DomainEntity.Review> {
    return this?.map {
        DomainEntity.Review(
            beerId = it.beerId.orZero(),
            content = it.content.orEmpty(),
            reviewId = it.reviewId.orZero(),
            star = it.star.orZero(),
            userId = it.userId.orEmpty(),
            createdDate = it.createdAt.orEmpty(),
            updatedDate = it.updatedAt.orEmpty(),
        )
    }.orEmpty()
}


fun ReviewResponse?.toReview(): DomainEntity.Review {
    return DomainEntity.Review(
        beerId = this?.beerId.orZero(),
        content = this?.content.orEmpty(),
        reviewId = this?.reviewId.orZero(),
        star = this?.star.orZero(),
        userId = this?.userId.orEmpty(),
        createdDate = this?.createdAt.orEmpty(),
        updatedDate = this?.updatedAt.orEmpty(),
    )
}

fun MyReviewListResponse?.toMyReviewList(): List<DomainEntity.MyReview> {
    return this?.myReview?.map {
        DomainEntity.MyReview(
            beer = it.beer.toBeer(),
            review = it.review.toReview()
        )
    }.orEmpty()
}

fun SearchResponse?.toBeerList(): DomainEntity.Beers {
    return DomainEntity.Beers(
        beers = this?.beers.toBeer(),
        page = DomainEntity.Page(
            totalCount = this?.totalCount.orZero(),
            totalPage = this?.totalPage.orZero(),
            currentPage = this?.currentPage.orZero(),
            previousPage = this?.previousPage.orZero(),
            nextPage = this?.nextPage.orZero()
        )
    )
}

fun NetworkResponse<LevelGuideResponse>?.toLevelGuide(): DomainEntity.Response<DomainEntity.Level> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = DomainEntity.Level(
            myLevel = DomainEntity.MyLevel(
                level = this?.data?.user?.level.orEmpty(),
                count = this?.data?.user?.count.orZero(),
                nextLevel = this?.data?.user?.nextLevel.orEmpty()
            ),
            levelGuide = this?.data?.levels?.map {
                DomainEntity.LevelGuide(
                    id = it.id.orZero(),
                    level = it.level.orEmpty(),
                    levelImage = it.levelImage.orEmpty(),
                    levelCount = it.levelCount.orZero(),
                )
            }.orEmpty()
        )
    )
}

fun NetworkResponse<AromaListResponse>?.toAromaList(): DomainEntity.Response<List<DomainEntity.Aroma>> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = this?.data?.aromaList?.map {
            DomainEntity.Aroma(
                id = it.id.orZero(),
                name = it.name.orEmpty(),
                isSelected = it.isSelected.orFalse()
            )
        }.orEmpty()
    )
}

fun NetworkResponse<List<StyleLargeCategoryResponse>>?.toStyleLargeCategory(): DomainEntity.Response<List<DomainEntity.StyleLargeCategory>> {
    return DomainEntity.Response(
        isSuccess = this?.isSuccess.orFalse(),
        message = this?.message.orEmpty(),
        data = this?.data?.map {
            DomainEntity.StyleLargeCategory(
                largeName = it.largeName.orEmpty(),
                middleCategories = it.middleCategories.toStyleMiddleCategory()
            )
        }.orEmpty()
    )
}

fun List<StyleMiddleCategoryResponse>?.toStyleMiddleCategory(): List<DomainEntity.StyleMiddleCategory> {
    return this?.map {
        DomainEntity.StyleMiddleCategory(
            middleName = it.middleName.orEmpty(),
            description = it.description.orEmpty(),
            smallCategories = it.smallCategories.toStyleSmallCategory(),
        )
    }.orEmpty()
}


fun List<StyleSmallCategoryResponse>?.toStyleSmallCategory(): List<DomainEntity.StyleSmallCategory> {
    return this?.map {
        DomainEntity.StyleSmallCategory(
            smallName = it.smallName.orEmpty()
        )
    }.orEmpty()
}
