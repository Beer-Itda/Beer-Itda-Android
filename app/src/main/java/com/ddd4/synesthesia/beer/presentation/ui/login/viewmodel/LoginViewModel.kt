package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val useCase: LoginUseCaseGroup,
) : BaseViewModel() {

    fun login(accessToken: String) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            useCase.login.execute(accessToken)
            statusSuccess()
            notifyActionEvent(LoginActionEntity.SuccessLogin)
        }
    }
}