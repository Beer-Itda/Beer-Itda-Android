package com.hjiee.data.mapper

import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import com.hjiee.data.response.common.EntityResponse
import com.hjiee.data.response.v2.*
import com.hjiee.domain.entity.DomainEntity

fun EntityResponse<BeerResponse>.toDomainEntity(): DomainEntity.Response<DomainEntity.Beer> {
    return DomainEntity.Response(
        isSuccess = isSuccess.orFalse(),
        message = message.orEmpty(),
        data = DomainEntity.Beer(
            id = data?.id.orZero(),
            abv = data?.starAvg.orZero(),
            krName = data?.krName.orEmpty(),
            engName = data?.engName.orEmpty(),
            thumbnailImage = data?.thumbnailImage.orEmpty(),
        )
    )
}

fun EntityResponse<BeersResponse>.toDomainEntityList(): DomainEntity.Response<DomainEntity.Beers> {
    return DomainEntity.Response(
        isSuccess = isSuccess.orFalse(),
        message = message.orEmpty(),
        data = DomainEntity.Beers(
            beers = data?.beers?.map {
                DomainEntity.Beer(
                    id = it.id.orZero(),
                    abv = it.starAvg.orZero(),
                    krName = it.krName.orEmpty(),
                    engName = it.engName.orEmpty(),
                    thumbnailImage = it.thumbnailImage.orEmpty(),
                )
            }.orEmpty(),
            cursor = DomainEntity.Cursor(data?.cursor?.cursor.orZero())
        )
    )
}

fun EntityResponse<AromasResponse>.toDomainEntityAroma(): DomainEntity.Response<DomainEntity.Beers> {
    return DomainEntity.Response(
        isSuccess = isSuccess.orFalse(),
        message = message.orEmpty(),
        data = DomainEntity.Beers(
            beers = data?.beers?.map {
                DomainEntity.Beer(
                    id = it.id.orZero(),
                    abv = it.starAvg.orZero(),
                    krName = it.krName.orEmpty(),
                    engName = it.engName.orEmpty(),
                    thumbnailImage = it.thumbnailImage.orEmpty(),
                )
            }.orEmpty(),
            cursor = DomainEntity.Cursor(data?.cursor?.cursor.orZero())
        )
    )
}


fun BeerResponse.toDomainEntity(): DomainEntity.Beer {
    return DomainEntity.Beer(
        id = id.orZero(),
        krName = krName.orEmpty(),
        abv = starAvg.orZero(),
        engName = engName.orEmpty(),
        thumbnailImage = thumbnailImage.orEmpty()
//        aromas = aromas.orEmpty(),
//        beerStyle = beerStyle.orEmpty(),
//        brewery = brewery.orEmpty(),
//        country = country.orEmpty(),
//        imageUrl = imageUrl.orEmpty(),
//        rateAvg = rateAvg.orZero(),
//        reviewList = reviews?.toDomainEntity().orEmpty(),
//        isFavorite = favoriteFlag.orFalse(),
    )
}

fun List<BeerResponse>?.toDomainEntity(): List<DomainEntity.Beer> {
    return this?.map {
        DomainEntity.Beer(
            id = it.id.orZero(),
            krName = it.krName.orEmpty(),
            abv = it.starAvg.orZero(),
            engName = it.engName.orEmpty(),
            thumbnailImage = it.thumbnailImage.orEmpty()
//        aromas = aromas.orEmpty(),
//        beerStyle = beerStyle.orEmpty(),
//        brewery = brewery.orEmpty(),
//        country = country.orEmpty(),
//        imageUrl = imageUrl.orEmpty(),
//        rateAvg = rateAvg.orZero(),
//        reviewList = reviews?.toDomainEntity().orEmpty(),
//        isFavorite = favoriteFlag.orFalse(),
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

//fun List<Review>.toDomainEntity(): List<DomainEntity.Review> {
//    return map {
//        DomainEntity.Review(
//            userId = it.userId.orZero(),
//            nickName = it.nickname.orEmpty(),
//            ratio = it.ratio.orZero(),
//            time = it.date?.time.orZero(),
//            beer = it.beer?.toDomainEntity(),
//            content = it.content.orEmpty()
//        ).apply {
//            createdAt = SimpleDateFormat("yyyy. MM. dd").format(it.date)
//        }
//    }
//}