package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.util.NetworkStatus
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.hjiee.core.Consts.ACCESS_TOKEN
import com.hjiee.core.Consts.REFRESH_TOKEN
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.domain.repository.LoginRepository
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val preference: SharedPreferenceProvider
) : BaseViewModel() {

    val isLoginSuccess = SingleLiveEvent<Pair<User?, Throwable?>>()
    val isLogoutSuccess = SingleLiveEvent<Boolean>()

    fun accessToken(code: String?) {
        viewModelScope.launch(errorHandler) {
//            loginRepository.accessToken(code) {
//                it?.run {
//                    TokenManagerProvider.instance.manager.setToken(
//                        OAuthToken(
//                            accessToken,
//                            Date(expiresIn.toLong()),
//                            refreshToken,
//                            Date(refreshTokenExpiresIn.toLong()),
//                            scope.split(",")
//                        )
//                    )
//                    accessToken.let { token ->
//                        preference.setPreference("token", token)
//                        login()
//                    }
//                }
//            }

        }
    }

    fun login(token: String) {
        statusLoading()
        viewModelScope.launch(errorHandler) {
            loginRepository.login(token).run {
                preference.setValue(ACCESS_TOKEN, accessToken)
                preference.setValue(REFRESH_TOKEN, refreshToken)
                notifyActionEvent(LoginActionEntity.SuccessLogin)
                statusSuccess()
            }
        }
    }
//        user?.let {
//            FirebaseCrashlytics.getInstance().setUserId(user.id.toString())
//            isLoginSuccess.call(Pair(user, null))
//        } ?: kotlin.run { isLoginSuccess.call(Pair(null, error)) }
//    }
//
//    fun logout() = loginRepository.logout {
//        isLogoutSuccess.call(it)
//    }
//
//    fun unlink() = loginRepository.unlink {
//        isLogoutSuccess.call(it)
//    }
}