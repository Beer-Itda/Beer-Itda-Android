package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWriteNicknameBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailActivity
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.KeyStringConst
import com.ddd4.synesthesia.beer.util.SimpleCallback

class WriteNickNameActivity : BaseActivity<ActivityWriteNicknameBinding>(R.layout.activity_write_nickname) {

    private val nickName by lazy { intent.extras?.getString(KEY_NICKNAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            edtNickname.apply {
                contents = nickName
                requestFocus()
            }
            ivClose.setOnClickListener { notice() }
            ivClear.setOnClickListener { contents = "" }
            btnSave.setOnClickListener {
                SimpleCallback.callback?.call(edtNickname.text.toString())
                finish()
            }
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
        if(binding.contents == nickName) {
            finish()
        } else {
            CustomAlertDialog(
                title = getString(R.string.page_out),
                message = getString(R.string.do_not_save),
                posivie = getString(R.string.yes),
                negative = getString(R.string.no),
                result = DialogInterface.OnClickListener { dialog, which -> finish() }
            ).show(supportFragmentManager,null)
        }
    }

    companion object {
        const val KEY_NICKNAME = "nickname"
        @JvmStatic
        fun start(context: Context, nickname: String) {
            context.startActivity(Intent(context, WriteNickNameActivity::class.java).apply {
                putExtra(KEY_NICKNAME, nickname)
            })
        }
    }
}