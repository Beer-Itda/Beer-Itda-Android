package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.item.LevelMapper.getItems
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.model.LevelActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.view.LevelStringProvider
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.mypage.level.LevelGuideUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelGuideViewModel @Inject constructor(
    private val useCase: LevelGuideUseCase,
    private val stringProvider: LevelStringProvider
) : BaseViewModel() {

    private val _message = MutableLiveData<CharSequence>()
    val message: LiveData<CharSequence> get() = _message

    fun load() {
        viewModelScope.launch {
            runCatching {
                useCase.execute()
            }.onSuccess {
                _message.value = stringProvider.getMyLevelMessage(it?.myLevel)
                notifyActionEvent(
                    LevelActionEntity.UpdateUi(
                        it?.levelGuide?.getItems(it.myLevel.id).orEmpty()
                    )
                )
            }.onFailure {
                L.e(it)
            }
        }
    }
}