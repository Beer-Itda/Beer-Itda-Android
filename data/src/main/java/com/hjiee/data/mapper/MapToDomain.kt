package com.hjiee.data.mapper

import com.hjiee.core.ext.orDefault
import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import com.hjiee.data.response.common.NetworkResponse
import com.hjiee.data.response.common.PageResponse
import com.hjiee.data.response.v2.*
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.entity.DomainEntity.Review.Companion.DEFAULT_STAR

fun PageResponse<BeerResponse>?.toBeerListWithPagination(): DomainEntity.PageResult<DomainEntity.Beer> {
    return DomainEntity.PageResult(
        data = this?.data?.map {
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
        }.orEmpty(),
        totalCount = this?.totalCount.orZero(),
        page = DomainEntity.Page(
            totalPage = this?.totalPage.orZero(),
            currentPage = this?.currentPage.orZero(),
            previousPage = this?.previousPage.orZero(),
            nextPage = this?.nextPage.orZero()
        )
    )
}

fun PageResponse<ReviewResponse>?.toReviewListWithPagination(): DomainEntity.PageResult<DomainEntity.Review> {
    return DomainEntity.PageResult(
        data = this?.data?.map {
            DomainEntity.Review(
                beerId = it.beerId.orZero(),
                content = it.content.orEmpty(),
                reviewId = it.reviewId.orZero(),
                _star = it.star.orDefault(DEFAULT_STAR),
                userId = it.userId.orEmpty(),
                createdDate = it.createdAt.orEmpty(),
                updatedDate = it.updatedAt.orEmpty(),
            )
        }.orEmpty(),
        totalCount = this?.totalCount.orZero(),
        page = DomainEntity.Page(
            totalPage = this?.totalPage.orZero(),
            currentPage = this?.currentPage.orZero(),
            previousPage = this?.previousPage.orZero(),
            nextPage = this?.nextPage.orZero()
        )
    )
}

fun BeerDetailResponse?.toBeerDetail(): DomainEntity.BeerDetail? {
    return this?.let {
        DomainEntity.BeerDetail(
            beer = beerDetail.toBeer(),
            reviewCount = review?.reviewCount.orZero(),
            myReview = review?.myReview.toReview(),
            review = review?.beerReviewList.toReviewList(),
            relatedAromaBeer = sameAromaBeers?.toBeer().orEmpty(),
            relatedStyleBeer = sameStyleBeers?.toBeer().orEmpty()
        )
    }
}

fun NetworkResponse<BeerResponse>?.toBeer(): DomainEntity.Response<DomainEntity.Beer> {
    return DomainEntity.Response(
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

fun LoginResponse?.toTokenInfo(): DomainEntity.LoginInfo {
    return DomainEntity.LoginInfo(
        accessToken = this?.accessToken.orEmpty(),
        refreshToken = this?.refreshToken.orEmpty(),
        isFirstSignupUser = this?.isFirstSignupUser.orFalse()
    )
}

fun UserResponse?.toUserInfo(): DomainEntity.User? {
    return this?.let {
        DomainEntity.User(
            id = id.orZero(),
            email = email.orEmpty(),
            nickName = nickname.orEmpty(),
            profileImage = "",
            myLevel = DomainEntity.MyLevel(
                id = reviewLevel?.currentLevelId.orZero(),
                levelImage = reviewLevel?.currentLevelImage.orEmpty(),
                currentLevel = reviewLevel?.currentLevel.orEmpty(),
                nextLevel = reviewLevel?.nextLevel.orEmpty(),
                needToReviewCount = reviewLevel?.needReviewCount.orZero(),
                currentReviewCount = reviewLevel?.currentReviewCount.orZero()
            ),
        )
    }
}

fun List<ReviewResponse>?.toReviewList(): List<DomainEntity.Review> {
    return this?.map {
        DomainEntity.Review(
            beerId = it.beerId.orZero(),
            content = it.content.orEmpty(),
            reviewId = it.reviewId.orZero(),
            _star = it.star.orDefault(DEFAULT_STAR),
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
        _star = this?.star.orDefault(DEFAULT_STAR),
        userId = this?.userId.orEmpty(),
        createdDate = this?.createdAt.orEmpty(),
        updatedDate = this?.updatedAt.orEmpty(),
    )
}

fun PageResponse<MyReviewItemResponse>?.toMyReviewList(): DomainEntity.PageResult<DomainEntity.MyReview> {
    return DomainEntity.PageResult(
        totalCount = this?.totalCount.orZero(),
        data = this?.data?.map {
            DomainEntity.MyReview(
                beer = it.beer.toBeer(),
                review = it.review.toReview()
            )
        }.orEmpty(),
        page = DomainEntity.Page(
            totalPage = this?.totalPage.orZero(),
            currentPage = this?.currentPage.orZero(),
            previousPage = this?.previousPage.orZero(),
            nextPage = this?.nextPage.orZero()
        )
    )

}

fun LevelGuideResponse?.toLevelGuide(): DomainEntity.Level? {
    return this?.let {
        DomainEntity.Level(
            myLevel = DomainEntity.MyLevel(
                id = this.reviewLevel.currentLevelId.orZero(),
                levelImage = this.reviewLevel.currentLevelImage.orEmpty(),
                currentLevel = this.reviewLevel.currentLevel.orEmpty(),
                needToReviewCount = this.reviewLevel.needReviewCount.orZero(),
                nextLevel = this.reviewLevel.nextLevel.orEmpty(),
                currentReviewCount = this.reviewLevel.currentReviewCount.orZero()
            ),
            levelGuide = this.levels?.map {
                DomainEntity.LevelGuide(
                    id = it.id.orZero(),
                    level = it.level.orEmpty(),
                    levelImage = it.levelImage.orEmpty(),
                    levelCount = it.levelCount.orZero(),
                )
            }.orEmpty()
        )
    }
}

fun AromaListResponse?.toAromaList(): List<DomainEntity.Aroma> {
    return this?.aromaList?.map {
        DomainEntity.Aroma(
            id = it.id.orZero(),
            name = it.name.orEmpty(),
            isSelected = it.isSelected.orFalse()
        )
    }.orEmpty()
}

fun StyleListResponse?.toStyleLargeCategory(): List<DomainEntity.StyleLargeCategory> {
    return this?.styleList?.map {
        DomainEntity.StyleLargeCategory(
            largeId = it.largeId.orZero(),
            largeName = it.largeName.orEmpty(),
            middleCategories = it.middleCategories.toStyleMiddleCategory()
        )
    }.orEmpty()
}

fun List<StyleMiddleCategoryResponse>?.toStyleMiddleCategory(): List<DomainEntity.StyleMiddleCategory> {
    return this?.map {
        DomainEntity.StyleMiddleCategory(
            middleId = it.middleId.orZero(),
            middleName = it.middleName.orEmpty(),
            description = it.description.orEmpty(),
            smallCategories = it.smallCategories.toStyleSmallCategory(),
        )
    }.orEmpty()
}


fun List<StyleSmallCategoryResponse>?.toStyleSmallCategory(): List<DomainEntity.StyleSmallCategory> {
    return this?.map {
        DomainEntity.StyleSmallCategory(
            smallId = it.smallId.orZero(),
            smallName = it.smallName.orEmpty(),
            isSelected = it.isSelected.orFalse()
        )
    }.orEmpty()
}
