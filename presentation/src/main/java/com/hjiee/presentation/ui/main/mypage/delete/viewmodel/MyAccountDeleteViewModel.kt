package com.hjiee.presentation.ui.main.mypage.delete.viewmodel

import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.ui.main.mypage.delete.entity.MyAccountDeleteActionEntity
import com.hjiee.presentation.ui.main.mypage.delete.entity.MyAccountDeleteSelectEntity
import com.hjiee.domain.usecase.login.AccountDeleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountDeleteViewModel @Inject constructor(
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