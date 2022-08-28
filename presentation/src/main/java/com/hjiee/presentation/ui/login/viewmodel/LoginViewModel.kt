package com.hjiee.presentation.ui.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.hjiee.presentation.base.BaseViewModel
import com.hjiee.presentation.base.model.ErrorActionEntity
import com.hjiee.presentation.ui.login.model.LoginActionEntity
import com.hjiee.presentation.ui.login.view.LoginStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCaseGroup,
    private val stringProvider: LoginStringProvider
) : BaseViewModel() {

    val noticeMessage get() = stringProvider.getNoticeMessage()

    fun login(accessToken: String) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            val result = useCase.login.execute(accessToken)
            if (result.accessToken.isNotEmpty()) {
                statusSuccess()
                notifyActionEvent(LoginActionEntity.SuccessLogin)
            } else {
                statusFailure()
                notifyActionEvent(ErrorActionEntity.ShowErrorMessage(stringProvider.loginFail()))
            }
        }
    }
}