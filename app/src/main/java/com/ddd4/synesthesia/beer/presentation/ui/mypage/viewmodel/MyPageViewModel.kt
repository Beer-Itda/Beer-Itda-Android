package com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.util.AppConfig
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository,
    private val beerRepository: BeerRepository,
    private val appConfig : AppConfig
) : BaseViewModel() {

    val isUnConnected = SingleLiveEvent<Boolean>()

    private val _userInfo = MutableLiveData<User?>()
    val userInfo : LiveData<User?> get() = _userInfo

    private val _nickName = MutableLiveData<String>()
    val nickName : LiveData<String> get() = _nickName

    init {
        me()
        userInfo()
    }

    fun generateInfoList() : List<MyInfo> = arrayListOf(
        // 내 활동
        MyInfo(InfomationsData.ACTIVE.title, InfomationsData.ACTIVE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.REVIEW.title, InfomationsData.REVIEW.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.FAVORITE.title, InfomationsData.FAVORITE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.RECENTLY_VISIT_TIME.title, InfomationsData.RECENTLY_VISIT_TIME.type,appConfig.lastVisitTime,eventNotifier = this@MyPageViewModel),
        // 고객센터 & 도움말
        MyInfo(InfomationsData.HELP.title, InfomationsData.HELP.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.NOTICE.title, InfomationsData.NOTICE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.CONTACT.title, InfomationsData.CONTACT.type,eventNotifier = this@MyPageViewModel),
        // 설정
        MyInfo(InfomationsData.SETTING.title, InfomationsData.SETTING.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.PUSH.title, InfomationsData.PUSH.type,eventNotifier = this@MyPageViewModel),
        // 서비스 정보
        MyInfo(InfomationsData.SERVICE_INFO.title, InfomationsData.SERVICE_INFO.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.TERMS_OF_USE.title, InfomationsData.TERMS_OF_USE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.PRIVACY_POLICY.title, InfomationsData.PRIVACY_POLICY.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.RELEASE_NOTE.title, InfomationsData.RELEASE_NOTE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.OPEN_SOURCE_LIB.title, InfomationsData.OPEN_SOURCE_LIB.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.PLAY_STORE.title, InfomationsData.PLAY_STORE.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.APP_VERSION.title, InfomationsData.APP_VERSION.type,appConfig.version,eventNotifier = this@MyPageViewModel),
        // 로그아웃 & 회원탈퇴
        MyInfo(InfomationsData.LOGOUT.title, InfomationsData.LOGOUT.type,eventNotifier = this@MyPageViewModel),
        MyInfo(InfomationsData.UNLINK.title, InfomationsData.UNLINK.type,eventNotifier = this@MyPageViewModel)
    )

    private fun me() = loginRepository.me {
        _userInfo.value = it
    }

    private fun userInfo() {
        viewModelScope.launch {
            _nickName.value = beerRepository.getUserInfo()?.nickname
        }
    }

    fun updateUserInfo(nickName : String?) {
        viewModelScope.launch {
            beerRepository.postUserInfo(nickName)
        }
    }

    fun logout() = loginRepository.logout {
        isUnConnected.call(it)
    }

    fun unlink() = loginRepository.unlink {
        isUnConnected.call(it)
    }

    fun clickModify() {
        notifySelectEvent(MyPageClickEntity.Modify)
    }

    override fun onCleared() {
        super.onCleared()
    }
}