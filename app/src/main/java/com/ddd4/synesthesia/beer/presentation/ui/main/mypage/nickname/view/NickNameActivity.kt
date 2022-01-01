package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWriteNicknameBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.nickname.viewmodel.NickNameViewModel
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.RegexUtil.isValidNickName
import com.ddd4.synesthesia.beer.util.SimpleCallback
import com.hjiee.core.util.listener.setOnDebounceClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NickNameActivity :
    BaseActivity<ActivityWriteNicknameBinding>(R.layout.activity_write_nickname) {

    private val viewModel by viewModels<NickNameViewModel>()
    private val nickName by lazy { intent.extras?.getString(KEY_NICKNAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = this@NickNameActivity.viewModel
            edtNickname.apply {
                requestFocus()
            }
            includeToolbar.toolbar.setOnDebounceClickListener { notice() }
            btnSave.setOnDebounceClickListener {
                SimpleCallback.callback?.call(edtNickname.text.toString())
                finish()
            }
        }
        initObserver()
    }

    override fun initObserver() {
        viewModel.nickName.observe(this@NickNameActivity) {
            viewModel.setIsValid(isValidNickName(it.toString()))
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.apply {
            edtNickname.setSelection(edtNickname.text.length)
        }
    }

    override fun onBackPressed() {
        notice()
    }

    private fun notice() {
        if (viewModel.nickName.value == nickName) {
            finish()
        } else {
            CustomAlertDialog(
                title = getString(R.string.page_out),
                message = getString(R.string.do_not_save),
                posivie = getString(R.string.yes),
                negative = getString(R.string.no),
                result = DialogInterface.OnClickListener { dialog, which -> finish() }
            ).show(supportFragmentManager, null)
        }
    }

    companion object {
        const val KEY_NICKNAME = "nickname"

        fun getIntent(
            context: Context,
            nickName: String?
        ): Intent {
            return Intent(context, NickNameActivity::class.java).apply {
                putExtra(KEY_NICKNAME, nickName)
            }
        }
    }
}