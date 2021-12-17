package com.ddd4.synesthesia.beer.presentation.ui.splash.view

import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivitySplashBinding
import com.ddd4.synesthesia.beer.util.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.ddd4.synesthesia.beer.presentation.ui.splash.viewmodel.SplashViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.hjiee.core.Consts.ACCESS_TOKEN
import com.hjiee.core.event.entity.ActionEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRemoteConfig()
        startLogin()
        initObserver()
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is LoginActionEntity.SuccessLogin -> {
                start<MainActivity>()
                finish()
            }
            is LoginActionEntity.FailLogin -> {
                start<LoginActivity>()
                finish()
            }
        }
    }

    override fun onBackPressed() {
        // Nothing
    }

    private fun startLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            viewModel.autoLogin()
        }
    }
}