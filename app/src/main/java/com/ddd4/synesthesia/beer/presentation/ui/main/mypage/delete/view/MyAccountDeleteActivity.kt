package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityAccountDeleteBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.entity.MyAccountDeleteActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.entity.MyAccountDeleteSelectEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.viewmodel.MyAccountDeleteViewModel
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 회원 탈퇴
 */
@AndroidEntryPoint
class MyAccountDeleteActivity :
    BaseActivity<ActivityAccountDeleteBinding>(R.layout.activity_account_delete) {

    private val viewModel by viewModels<MyAccountDeleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@MyAccountDeleteActivity.viewModel

            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is MyAccountDeleteActionEntity.DeleteSuccess -> {
                showToast(getString(R.string.delete_success))
                start<LoginActivity>(LoginActivity.getTaskIntent(this))
            }
            is MyAccountDeleteActionEntity.DeleteFail -> {
                showToast(getString(R.string.delete_fail))
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is MyAccountDeleteSelectEntity.Delete -> {
                CustomAlertDialog(
                    title = getString(R.string.delete_account),
                    message = getString(R.string.unlink_message),
                    posivie = getString(R.string.yes),
                    negative = getString(R.string.no),
                    result = DialogInterface.OnClickListener { dialog, which ->
                        viewModel.deleteAccount()
                    }
                ).show(supportFragmentManager, null)
            }
        }
    }

    companion object {

        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, MyAccountDeleteActivity::class.java).apply {
            }
        }
    }
}