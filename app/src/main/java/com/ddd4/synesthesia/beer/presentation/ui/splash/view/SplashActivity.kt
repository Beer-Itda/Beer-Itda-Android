package com.ddd4.synesthesia.beer.presentation.ui.splash.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivitySplashBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.model.LoginActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.ddd4.synesthesia.beer.presentation.ui.splash.model.SplashActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.splash.viewmodel.SplashViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()
    private val dialog by lazy {
        AlertDialog.Builder(this, R.style.Dialog).apply {
            setCancelable(false)
            setPositiveButton(getString(R.string.update)) { _, _ ->
                moveToPlayStore()
            }
            setNegativeButton(getString(R.string.no_update)) { _, _ ->
                viewModel.autoLogin()
            }
        }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkForUpdate()
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
            is SplashActionEntity.ForceUpdate -> {
                showDialog(
                    title = getString(R.string.force_update_title),
                    message = entity.message
                )
            }
            is SplashActionEntity.RecommendUpdate -> {
                showDialog(
                    title = getString(R.string.recommend_update_title),
                    message = entity.message
                )
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        if (dialog.isShowing) {
            return
        }

        dialog.apply {
            setTitle(title)
            setMessage(message)

        }.show()
    }

//    override fun onBackPressed() {
    // Nothing
//    }
}