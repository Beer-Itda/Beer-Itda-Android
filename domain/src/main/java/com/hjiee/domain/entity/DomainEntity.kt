package com.hjiee.domain.entity

import java.text.SimpleDateFormat
import java.util.*


sealed class DomainEntity {
    data class TokenInfo(
        val accessToken: String,
        val refreshToken: String
    )

    data class Response<ELEMENT>(
        val data: ELEMENT
    )

    data class PageResult<ELEMENT>(
        val totalCount: Int,
        val data: List<ELEMENT>,
        val page: Page
    )

    data class Page(
        var totalPage: Int = 0,
        var currentPage: Int = 0,
        var previousPage: Int = 0,
        var nextPage: Int = 0
    ) {
        fun clear() {
            totalPage = 0
            currentPage = 0
            previousPage = 0
            nextPage = 0
        }

        fun hasNext() = totalPage - 1 > currentPage

    }

    data class Beers(
        val beers: List<Beer>,
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
        val beer: Beer,
        val myReview: Review,
        val reviewCount: Float,
        val review: List<Review>,
        val relatedStyleBeer: List<Beer>,
        val relatedAromaBeer: List<Beer>
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
        _star: Float = DEFAULT_STAR,
        createdDate: String,
        updatedDate: String
    ) {
        companion object {
            const val DEFAULT_STAR = 0.5f
        }

        val star = if (_star == 0f) {
            DEFAULT_STAR
        } else {
            _star
        }

        private val formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private val formatString = SimpleDateFormat("yyyy. MM. dd")
        private val parsedCreatedDate = try {
            formatDate.parse(createdDate)
        } catch (e: Exception) {
            Date()
        }
        private val parsedUpdatedDate = try {
            formatDate.parse(updatedDate)
        } catch (e: Exception) {
            Date()
        }

        val createdAt: String = formatString.format(parsedCreatedDate)
        val updatedAt: String = formatString.format(parsedUpdatedDate)
    }

    class User(
        val id: Int,
        val email: String,
        val nickName: String,
        val profileImage: String,
        val myLevel: MyLevel
    )

    class Level(
        val myLevel: MyLevel,
        val levelGuide: List<LevelGuide>
    )

    class MyLevel(
        val id: Int,
        val levelImage: String,
        val needToReviewCount: Int,
        val currentReviewCount: Int,
        val currentLevel: String,
        val nextLevel: String
    ) {
        val needToNextLevelReviewCount = currentReviewCount + needToReviewCount
    }

    class LevelGuide(
        val id: Int,
        val level: String,
        val levelImage: String,
        val levelCount: Int
    )

    class Aroma(
        val id: Int,
        val name: String,
        val isSelected: Boolean
    )

    class StyleLargeCategory(
        val largeId: Int,
        val largeName: String,
        val middleCategories: List<StyleMiddleCategory>
    )

    class StyleMiddleCategory(
        val middleId: Int,
        val middleName: String,
        val description: String,
        val smallCategories: List<StyleSmallCategory>
    )

    class StyleSmallCategory(
        val smallId: Int,
        val smallName: String,
        val isSelected: Boolean
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