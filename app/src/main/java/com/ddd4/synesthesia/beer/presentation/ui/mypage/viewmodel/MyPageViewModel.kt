package com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel

class MyPageViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository
) : BaseViewModel() {



    fun generateInfoList() : List<MyInfo> = arrayListOf(
        MyInfo(InfomationsData.ACTIVE.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.STAR.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.REVIEW.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.HELP.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.NOTICE.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.CONTACT.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.SERVICE_INFO.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.TERMS_OF_USE.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.SETTING.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.PUSH.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.LOGOUT.title, InfomationsType.LOGOUT)
    )

    fun logout(callback : (Boolean) -> Unit) = loginRepository.logout {
        callback.invoke(it)
    }

    fun unlink(callback : (Boolean) -> Unit) = loginRepository.unlink {
        callback.invoke(it)
    }

    override fun onCleared() {
        super.onCleared()
    }
}