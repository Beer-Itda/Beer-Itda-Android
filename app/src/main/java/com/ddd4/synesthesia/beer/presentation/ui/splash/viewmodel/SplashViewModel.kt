package com.ddd4.synesthesia.beer.presentation.ui.splash.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.splash.model.SplashActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.splash.view.SplashStringProvider
import com.hjiee.core.manager.AppUpdate
import com.hjiee.core.manager.FirebaseConfigManager
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.usecase.login.GetTokenUseCase
import com.hjiee.domain.usecase.login.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: GetTokenUseCase,
    private val preference: SharedPreferenceProvider,
    private val stringProvider: SplashStringProvider,
    private val versionManager: VersionManager,
    private val firebaseConfigManager: FirebaseConfigManager
) : BaseViewModel() {

    init {
        firebaseConfigManager.fetchRemoteConfig()
    }

    fun checkForUpdate() {
        when (versionManager.updateInfo()) {
            AppUpdate.Force -> {
                notifyActionEvent(SplashActionEntity.ForceUpdate(stringProvider.getString(AppUpdate.Force)))
            }
            AppUpdate.Recommend -> {
                notifyActionEvent(SplashActionEntity.RecommendUpdate(stringProvider.getString(AppUpdate.Recommend)))
            }
            AppUpdate.None -> {
                viewModelScope.launch {
                    delay(1500)
                    autoLogin()
                }
            }
        }
    }

    fun autoLogin() {
        tokenUseCase.execute { accessToken ->
            if (accessToken.isEmpty()) {
                notifyActionEvent(LoginActionEntity.FailLogin)
            } else {
                viewModelScope.launch {
                    runCatching {
                        loginUseCase.execute(accessToken)
                    }.onSuccess {
                        notifyActionEvent(LoginActionEntity.SuccessLogin)
                    }.onFailure {
                        notifyActionEvent(LoginActionEntity.FailLogin)
                    }
                }
            }
        }
    }
}