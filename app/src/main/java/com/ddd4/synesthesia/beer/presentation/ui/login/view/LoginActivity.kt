package com.ddd4.synesthesia.beer.presentation.ui.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityLoginBinding
import com.ddd4.synesthesia.beer.util.ext.showSnackBar
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.viewmodel.LoginViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.ddd4.synesthesia.beer.util.HyperLinkMovement
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.hjiee.core.event.entity.ActionEntity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val loginViewModel by viewModels<LoginViewModel>()
    private val message by lazy { intent.getStringExtra(KEY_LOGIN) }
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
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

        message?.let {
            binding.root.showSnackBar(it)
        }
        intent.data?.let {
            it.query?.split("=")?.let { query ->
                if (query.isNotEmpty()) {
                    loginViewModel.accessToken(query[1])
                }
            }
        }
    }

    override fun initBind() {
        binding.apply {
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

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun startLogin() {
        // 카카오톡으로 로그인
//        loginViewModel.login()
        LoginClient.instance.apply {
            if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                loginWithKakaoTalk(context = this@LoginActivity, callback = callback)
            } else {
                loginWithKakaoAccount(this@LoginActivity, callback = callback)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun initObserver() {
        loginViewModel.isLoginSuccess.observe(this@LoginActivity, Observer {
            it.first?.let {
                start<MainActivity>(true)
            } ?: kotlin.run {
                showToast("${getString(R.string.fail_login)}\n${it.second?.message}")
                preference.clear()
            }
        })
        observeHandledEvent(loginViewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is LoginActionEntity.SuccessLogin -> {
                start<MainActivity>()
            }
        }
    }

    companion object {
        const val KEY_LOGIN = "login"

        @JvmStatic
        fun start(context: Context, message: String) {
            context.startActivity(Intent(context, LoginActivity::class.java).apply {
                putExtra(KEY_LOGIN, message)
            })
        }
    }
}