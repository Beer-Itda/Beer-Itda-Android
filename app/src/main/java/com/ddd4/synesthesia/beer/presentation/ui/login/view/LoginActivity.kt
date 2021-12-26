package com.ddd4.synesthesia.beer.presentation.ui.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityLoginBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.model.ErrorActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.ddd4.synesthesia.beer.util.HyperLinkMovement
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val message by lazy { intent.getStringExtra(KEY_LOGIN) }
    private val kakaoTalkLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.tag("tokenInfo").e(error)
            showToast(getString(R.string.fail_login))
        } else if (token != null) {
            Timber.tag("tokenInfo").d(token.accessToken)
            loginViewModel.login(token.accessToken)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            viewModel = loginViewModel
            tvLogin.setOnClickListener { startLogin() }
            with(tvLoginNotice) {
                text = HtmlCompat.fromHtml(
                    String.format(
                        getString(R.string.login_notice),
                        preference.getPreferenceString(getString(R.string.terms_of_use)),
                        preference.getPreferenceString(getString(R.string.privacy_policy))
                    ),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                movementMethod = HyperLinkMovement()
            }
        }
    }

    private fun startLogin() {
        // 카카오톡으로 로그인
        LoginClient.instance.apply {
            if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                loginWithKakaoTalk(context = this@LoginActivity, callback = kakaoTalkLoginCallback)
            } else {
                loginWithKakaoAccount(this@LoginActivity, callback = kakaoTalkLoginCallback)
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(loginViewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is LoginActionEntity.SuccessLogin -> {
                start<MainActivity>()
            }
            is ErrorActionEntity.ShowErrorMessage -> {
                showToast(entity.message)
            }
        }
    }

    companion object {
        const val KEY_LOGIN = "login"

        /**
         * 비회원 로그인 후 로그인처리가 필요할때 사용
         */
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {

            }
        }

        /**
         * 로그아웃, 회원탈퇴시 사용
         */
        @JvmStatic
        fun getTaskIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}