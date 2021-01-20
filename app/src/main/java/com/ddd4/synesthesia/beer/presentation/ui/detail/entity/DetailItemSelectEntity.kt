package com.ddd4.synesthesia.beer.presentation.ui.detail.entity

sealed class DetailItemSelectEntity {
    class Favorite(id : Int) : DetailItemSelectEntity()
}