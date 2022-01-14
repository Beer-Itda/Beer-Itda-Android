package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelMapper.getItem
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.model.LevelActionEntity
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.mypage.level.LevelGuideUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelGuideViewModel @Inject constructor(
    private val useCase: LevelGuideUseCase
) : BaseViewModel() {


    fun load() {
        viewModelScope.launch {
            runCatching {
                useCase.execute().getItem()
            }.onSuccess {
                notifyActionEvent(LevelActionEntity.UpdateUi(it))
            }.onFailure {
                L.e(it)
            }
        }
    }
}