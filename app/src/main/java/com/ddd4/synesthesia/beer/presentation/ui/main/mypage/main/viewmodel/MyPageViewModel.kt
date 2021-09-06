package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.entity.MyPageClickEntity
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    private val _userInfo = MutableLiveData<User?>()
    val userInfo: LiveData<User?> get() = _userInfo

    private val _nickName = MutableLiveData<String>()
    val nickName: LiveData<String> get() = _nickName

    private fun me() = loginRepository.me {
        _userInfo.value = it
    }

    fun load() {
        me()
        viewModelScope.launch(errorHandler) {
            _nickName.value = beerRepository.getUserInfo()?.nickname
        }
    }

    fun updateUserInfo(nickName: String?) {
        viewModelScope.launch(errorHandler) {
            beerRepository.postUserInfo(nickName)
        }
    }


    fun clickModify() {
        notifySelectEvent(MyPageClickEntity.Modify)
    }

    override fun onCleared() {
        super.onCleared()
    }
}