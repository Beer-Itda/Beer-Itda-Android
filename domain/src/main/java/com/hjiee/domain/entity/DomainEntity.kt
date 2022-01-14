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

    data class Page(
        var totalCount: Int = 0,
        var totalPage: Int = 0,
        var currentPage: Int = 0,
        var previousPage: Int = 0,
        var nextPage: Int = 0
    ) {
        fun clear() {
            totalCount = 0
            totalPage = 0
            currentPage = 0
            previousPage = 0
            nextPage = 0
        }

        fun hasNext() = totalPage - 1 > currentPage

    }

    data class Beers(
        val beers: List<Beer>,
        val cursor: Cursor? = null,
        val page: Page? = null
    )

    data class Beer(
        val id: Int,
        val abv: Float,
        val nameForKorean: String,
        val nameForEnglish: String,
        val starAvg: Float,
        val thumbnailImage: String,
        val isFavorite: Boolean,
        val brewery: String,
        val country: String,
        val style: String
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

        private val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private val formatString = SimpleDateFormat("yyyy. MM. dd")
        private val parsedCreatedDate = formatDate.parse(createdDate)
        private val parsedUpdatedDate = formatDate.parse(updatedDate)

        val createdAt: String = formatString.format(parsedCreatedDate)
        val updatedAt: String = formatString.format(parsedUpdatedDate)
    }

    class User(
        val id: Int,
        val email: String,
        val nickName: String,
        val reviewCount: Int,
        val levelId: Int,
        val profileImage: String
    )

    class LevelGuide(
        val id: Int,
        val level: String,
        val levelCount: Int
    )

    class Aroma(
        val id: Int,
        val name: String
    )

    class StyleLargeCategory(
        val largeName: String,
        val middleCategories: List<StyleMiddleCategory>
    )

    class StyleMiddleCategory(
        val middleName: String,
        val description: String,
        val smallCategories: List<StyleSmallCategory>
    )

    class StyleSmallCategory(
        val smallName: String
    )

    class Token(
        val tokenType: String,
        val accessToken: String,
        val expiresIn: Int,
        val refreshToken: String,
        val refreshTokenExpiresIn: Int,
        val scope: String
    )
}