package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.model.NickNameChangeActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.view.NickNameChangeActivity
import com.hjiee.core.manager.UserInfoManager
import com.hjiee.core.util.log.L
import com.hjiee.domain.usecase.mypage.NickNameChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NickNameChangeViewModel @Inject constructor(
    @Assisted private val savedState: SavedStateHandle,
    private val useCase: NickNameChangeUseCase
) : BaseViewModel() {

    val nickName = MutableLiveData<String>(savedState.get(NickNameChangeActivity.KEY_NICKNAME))
    val maxContentLength = 10

    private val _isValid = MutableLiveData<Boolean>(false)
    val isValid: LiveData<Boolean> get() = _isValid

    fun execute() {
        viewModelScope.launch {
            runCatching {
                useCase.execute(nickName = nickName.value.orEmpty())
            }.onSuccess {
                UserInfoManager.changed()
                notifyActionEvent(NickNameChangeActionEntity.NickNameUpdated)
            }.onFailure {
                L.e(it)
            }
        }
    }

    fun setIsValid(isValid: Boolean) {
        _isValid.value = isValid
    }

    fun clickClear() {
        nickName.value = ""
    }

    fun clickSave() {
        notifyActionEvent(NickNameChangeActionEntity.HideKeyboard)
        execute()
    }

}