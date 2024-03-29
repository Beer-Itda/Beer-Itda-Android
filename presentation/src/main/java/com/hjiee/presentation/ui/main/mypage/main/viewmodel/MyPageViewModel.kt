package com.hjiee.presentation.ui.main.mypage.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.ui.main.mypage.entity.MyPageClickEntity
import com.hjiee.presentation.ui.main.mypage.main.view.MyPageViewState
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.login.UserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
) : BaseViewModel() {

    private val _userInfo = MutableLiveData<DomainEntity.User?>()
    val userInfo: LiveData<DomainEntity.User?> get() = _userInfo

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> get() = _nickName

    val viewState = MyPageViewState()


    fun load() {
        viewModelScope.launch {
            runCatching {
                userInfoUseCase.execute()
            }.mapCatching {
                _userInfo.value = it
            }.onSuccess {
                viewState.isRefresh.set(false)
            }.onFailure {
                viewState.isRefresh.set(false)
            }
        }
    }

    fun refresh() {
        viewState.isRefresh.set(true)
        load()
    }


    fun clickModify() {
        notifySelectEvent(MyPageClickEntity.Modify)
    }

    fun clickProfile() {
        notifySelectEvent(MyPageClickEntity.Profile)
    }

    override fun onCleared() {
        super.onCleared()
    }
}