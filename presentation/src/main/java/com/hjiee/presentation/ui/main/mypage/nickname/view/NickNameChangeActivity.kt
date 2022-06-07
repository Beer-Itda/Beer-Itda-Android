package com.hjiee.presentation.ui.main.mypage.nickname.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityNicknameChangeBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.main.mypage.nickname.model.NickNameChangeActionEntity
import com.hjiee.presentation.ui.main.mypage.nickname.viewmodel.NickNameChangeViewModel
import com.hjiee.presentation.util.RegexUtil.isValidNickName
import com.hjiee.presentation.util.ext.hideKeyboard
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.showToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.util.listener.setOnDebounceClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NickNameChangeActivity :
    BaseActivity<ActivityNicknameChangeBinding>(R.layout.activity_nickname_change) {

    private val viewModel by viewModels<NickNameChangeViewModel>()
    private val nickName by lazy { intent.extras?.getString(KEY_NICKNAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = this@NickNameChangeActivity.viewModel
            edtNickname.apply {
                requestFocus()
            }
            includeToolbar.toolbar.setOnDebounceClickListener { notice() }
        }
        initObserver()
    }

    override fun initObserver() {
        viewModel.nickName.observe(this@NickNameChangeActivity) {
            viewModel.setIsValid(isValidNickName(it.toString()))
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is NickNameChangeActionEntity.NickNameUpdated -> {
                showToast(getString(R.string.changed_nickname))
                finish()
            }
            is NickNameChangeActionEntity.HideKeyboard -> {
                hideKeyboard(binding.root)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.run {
            edtNickname.setSelection(edtNickname.text.length)
        }
    }

    override fun onBackPressed() {
        notice()
    }

    private fun notice() {
        if (viewModel.nickName.value == nickName) {
            super.onBackPressed()
        } else {
            showDialog()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this@NickNameChangeActivity)
            .setTitle(getString(R.string.page_out))
            .setMessage(getString(R.string.do_not_save))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                finish()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    companion object {
        const val KEY_NICKNAME = "nickname"

        fun getIntent(
            context: Context,
            nickName: String?
        ): Intent {
            return Intent(context, NickNameChangeActivity::class.java).apply {
                putExtra(KEY_NICKNAME, nickName)
            }
        }
    }
}