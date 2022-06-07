package com.hjiee.presentation.ui.common.sort.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.util.sort.SortSetting
import com.hjiee.presentation.util.sort.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val sortSetting: SortSetting
) : BaseViewModel() {

    val sortType: LiveData<SortType> = sortSetting.getSort().asLiveData()

    fun sortWithType() {
//        sortSetting.sortType = sortType
    }

}