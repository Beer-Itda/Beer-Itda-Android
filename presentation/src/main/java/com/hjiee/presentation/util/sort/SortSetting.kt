package com.hjiee.presentation.util.sort

import kotlinx.coroutines.flow.Flow

interface SortSetting {

    var sortType: SortType
    fun getSort(): Flow<SortType>

}