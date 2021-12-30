package com.hjiee.domain.entity

import java.text.SimpleDateFormat

sealed class DomainEntity {
    data class TokenInfo(
        val accessToken: String,
        val refreshToken: String
    )

    data class Response<ELEMENT>(
        val isSuccess: Boolean,
        val message: String,
        val data: ELEMENT
    )

    data class Beers(
        val beers: List<Beer>,
        val cursor: Cursor?
    )

    data class Beer(
        val id: Int,
        val abv: Float,
        val nameForKorean: String,
        val nameForEnglish: String,
        val starAvg: Float,
        val thumbnailImage: String,
        val isFavorite: Boolean = false,
        val brewery: String = "",
        val country: String = "",
        val style: String = ""
//        val aromas: List<String>,
//        val rateAvg: Float,
//        val reviewList: List<Review>,
    )

    data class BeerDetail(
        val beer: Beer?,
        val relatedStyleBeer: List<Beer>,
        val relatedAromaBeer: List<Beer>
    )

    data class Cursor(
        val cursor: Int?
    )

    class MyReview(
        val beer: Beer,
        val review: Review
    )

    class Review(
        val reviewId: Int,
        val beerId: Int,
        val userId: String,
        val content: String,
        val star: Float,
        createdDate: String,
        updatedDate: String
    ) {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")


        val createdAt: String = ""
//        val updatedAt: String = SimpleDateFormat("yyyy. MM. dd").format(updatedDate)
        val updatedAt: String = ""
    }

    class User(
        val id: Int,
        val email: String,
        val nickName: String,
        val reviewCount: Int,
        val levelId: Int,
        val profileImage: String
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