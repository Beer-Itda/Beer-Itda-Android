package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginStringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCaseGroup,
    private val stringProvider: LoginStringProvider
) : BaseViewModel() {

    val noticeMessage get() = stringProvider.getNoticeMessage()

    fun login(accessToken: String) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            useCase.login.execute(accessToken)
            statusSuccess()
            notifyActionEvent(LoginActionEntity.SuccessLogin)
        }
    }
}