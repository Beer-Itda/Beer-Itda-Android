package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.entity.MyAccountDeleteActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.entity.MyAccountDeleteSelectEntity
import com.hjiee.domain.usecase.login.AccountDeleteUseCase
import kotlinx.coroutines.launch

class MyAccountDeleteViewModel @ViewModelInject constructor(
    private val useCase: AccountDeleteUseCase
//    private val preferenceProvider: SharedPreferenceProvider
) : BaseViewModel() {

    val viewState by lazy { MyAccountDeleteViewState() }

    fun deleteAccount() {
        viewModelScope.launch {
            runCatching {
                useCase.execute(
                    success = {
//                        preferenceProvider.clear()
                        notifyActionEvent(MyAccountDeleteActionEntity.DeleteSuccess)
                    },
                    failure = {
                        notifyActionEvent(MyAccountDeleteActionEntity.DeleteFail)
                    }
                )
            }
        }
    }

    fun clickDeleteAccount() {
        notifySelectEvent(MyAccountDeleteSelectEntity.Delete)
    }
}