package com.hjiee.domain.entity

import java.util.*

sealed class DomainEntity {
    interface BeerItem {

    }

    data class TokenInfo(
        val accessToken: String,
        val refreshToken: String
    )

    data class Response<ELEMENT>(
        val isSuccess: Boolean,
        val message: String,
        val data: ELEMENT?
    )

    data class Beers(
        val beers: List<Beer>,
        val cursor: Cursor?
    )

    data class Beer(
        val id: Int,
        val abv: Float,
        val krName: String,
        val engName: String,
        val thumbnailImage: String
//        val aromas: List<String>,
//        val beerStyle: String,
//        val brewery: String,
//        val country: String,
//        val imageUrl: List<String>,
//        val rateAvg: Float,
//        val reviewList: List<Review>,
//        val isFavorite: Boolean
    )

    data class Cursor(
        val cursor: Int?
    )

    class Review(
        val userId: Int,
        val nickName: String,
        val ratio: Float,
        val time: Long,
        val beer: Beer?,
        val content: String
    ) {
        var createdAt: String = ""
    }

    class User(
        val id: String,
        val externalId: String,
        val nickname: String,
        val profileImage: String,
        val thumbnailImage: String
    )

    class StyleLargeCategory(
        val bigName: String,
        val middleCategories: List<StyleMediumCategory>
    )

    class StyleMediumCategory(
        val middleName: String,
        val description: String,
        val smallCategories: List<String>
    )

    class Token(
        val tokenType: String,
        val accessToken: String,
        val expiresIn: Int,
        val refreshToken: String,
        val refreshTokenExpiresIn: Int,
        val scope: String
    )

    class Page(
        val nextCursor: Int?
    )

}