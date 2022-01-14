package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelItemViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.model.LevelActionEntity
import com.hjiee.core.util.log.L
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelGuideViewModel @Inject constructor(

) : BaseViewModel() {

    private val dummy = mutableListOf<LevelItemViewModel>()

    fun load() {
        viewModelScope.launch {
            runCatching {
                (0..5).map {
                    LevelItemViewModel(
                        id = it,
                        level = it.toString(),
                        levelImage = it.toString(),
                        levelCount = it,
                    )
                }
            }.onSuccess {
                notifyActionEvent(LevelActionEntity.UpdateUi(it))
            }.onFailure {
                L.e(it)
            }
        }
    }
}