package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.view.MyPageViewState
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.usecase.login.UserInfoUseCase
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
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

    fun updateUserInfo(nickName: String?) {
        viewModelScope.launch(errorHandler) {
//            beerRepository.postUserInfo(nickName)
        }
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