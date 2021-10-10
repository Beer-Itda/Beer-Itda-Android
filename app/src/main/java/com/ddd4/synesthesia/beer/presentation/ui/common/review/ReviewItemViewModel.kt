package com.ddd4.synesthesia.beer.presentation.ui.common.review

class ReviewItemViewModel(
    val reviewId: Int,
    val writerNickName: String,
    val ratio: Float,
    val content: String,
    val createdAt: String
) : IReviewListViewModel {

}