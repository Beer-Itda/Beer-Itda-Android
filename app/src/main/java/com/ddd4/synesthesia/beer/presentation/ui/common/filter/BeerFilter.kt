package com.ddd4.synesthesia.beer.presentation.ui.common.filter

data class BeerFilter(
    val styleFilter: List<String>? = null,
    val aromaFilter: List<String>? = null,
    val abvFilter: Pair<Int, Int>? = null,
    val countryFilter: List<String>? = null
)