package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val useCase: LoginUseCaseGroup,
) : BaseViewModel() {


    /**
     * 카카오 계정으로 로그인 한경우 리다이렉트되는 코드로 access token을 가져온다
     */
    fun accessToken(code: String?) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            val tokenInfo = useCase.token.execute(code.orEmpty())
            login(tokenInfo.accessToken)
        }
    }

    fun login(accessToken: String) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            useCase.login.execute(accessToken)
            statusSuccess()
            notifyActionEvent(LoginActionEntity.SuccessLogin)
        }
    }
}