package com.hjiee.presentation.ui.main.mypage.delete.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityAccountDeleteBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.login.view.LoginActivity
import com.hjiee.presentation.ui.main.mypage.delete.entity.MyAccountDeleteActionEntity
import com.hjiee.presentation.ui.main.mypage.delete.entity.MyAccountDeleteSelectEntity
import com.hjiee.presentation.ui.main.mypage.delete.viewmodel.MyAccountDeleteViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.showToast
import com.hjiee.presentation.util.ext.start
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                showToast(getString(R.string.user_delete_success))
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
                showDialog()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this@MyAccountDeleteActivity)
            .setTitle(getString(R.string.delete_account))
            .setMessage(getString(R.string.unlink_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteAccount()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
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