package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import com.hjiee.domain.usecase.login.GetTokenUseCase
import com.hjiee.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseGroup @Inject constructor(
    val token: GetTokenUseCase,
    val login: LoginUseCase
)