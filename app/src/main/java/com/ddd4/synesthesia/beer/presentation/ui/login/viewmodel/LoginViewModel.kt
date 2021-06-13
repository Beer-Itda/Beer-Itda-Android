package com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.ddd4.synesthesia.beer.util.provider.SharedPreferenceProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kakao.sdk.auth.TokenManagerProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

class LoginViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val preference: SharedPreferenceProvider
) : BaseViewModel() {

    val isLoginSuccess = SingleLiveEvent<Pair<User?, Throwable?>>()
    val isLogoutSuccess = SingleLiveEvent<Boolean>()

    fun accessToken(code: String?) {
        viewModelScope.launch(errorHandler) {
            loginRepository.accessToken(code) {
                it?.run {
                    TokenManagerProvider.instance.manager.setToken(
                        OAuthToken(
                            accessToken,
                            Date(expiresIn.toLong()),
                            refreshToken,
                            Date(refreshTokenExpiresIn.toLong()),
                            scope.split(",")
                        )
                    )
                    accessToken.let { token ->
                        preference.setPreference("token", token)
                        login()
                    }
                }
            }

        }
    }

    fun login() = loginRepository.login { user, error ->
        user?.let {
            FirebaseCrashlytics.getInstance().setUserId(user.id.toString())
            isLoginSuccess.call(Pair(user, null))
        } ?: kotlin.run { isLoginSuccess.call(Pair(null, error)) }
    }

    fun logout() = loginRepository.logout {
        isLogoutSuccess.call(it)
    }

    fun unlink() = loginRepository.unlink {
        isLogoutSuccess.call(it)
    }
}