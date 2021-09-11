package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.databinding.ActivityMyPageSettingBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.viewmodel.SettingViewModel
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity :
    BaseActivity<ActivityMyPageSettingBinding>(R.layout.activity_my_page_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val settingAdapter by lazy { SettingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.run {
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvSetting) {
                adapter = settingAdapter
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is SettingActionEntity.UpdateItem -> {
                settingAdapter.clear()
                settingAdapter.addAll(entity.items)
            }
            is SettingActionEntity.LogOut -> {
                preference.clear()
                LoginActivity.start(this@SettingActivity, getString(R.string.success_logout))
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is SettingClickEntity.ItemClick -> {
                informationsEvent(entity.item.title)
            }
        }
    }

    private fun informationsEvent(section: String) {
        when (section) {
            // 공지사항
            InfomationsData.NOTICE.title -> {
                moveToNotice()
            }
            // 문의하기
            InfomationsData.CONTACT.title -> {
                moveToContact()
            }
            // 릴리즈 노트
            InfomationsData.RELEASE_NOTE.title -> {
                moveToReleaseNote()
            }
            // 오픈소스 라이브러리
            InfomationsData.OPEN_SOURCE_LIB.title -> {
                start<OssLicensesMenuActivity>(false)
            }
            // 플레이 스토어 평가
            InfomationsData.PLAY_STORE.title -> {
                moveToPlayStore()
            }
            // 개인정보처리방침
            InfomationsData.PRIVACY_POLICY.title -> {
                moveToPrivacyPolicy()
            }
            // 이용약관
            InfomationsData.TERMS_OF_USE.title -> {
                moveToTermsOfUse()
            }
            // 로그아웃
            InfomationsData.LOGOUT.title -> {
                unConnected(getString(R.string.logout_message))
            }
            // 회원탈퇴
            InfomationsData.UNLINK.title -> {
                unConnected(getString(R.string.unlink_message))
            }
            // 알림설정
            InfomationsData.PUSH.title -> {
                showToast(resources.getString(R.string.please_wait_for_a_little_while))
            }
        }
    }


    private fun unConnected(message: String) {
        CustomAlertDialog(
            title = "",
            message = message,
            posivie = getString(R.string.yes),
            negative = getString(R.string.no),
            result = DialogInterface.OnClickListener { dialog, which ->
                when (message) {
                    getString(R.string.logout_message) -> {
                        viewModel.logout()
                    }
                    getString(R.string.unlink_message) -> {
                        viewModel.unlink()
                    }
                }
            }
        ).show(supportFragmentManager, null)
    }

    /**
     * 이용약관
     */
    private fun moveToTermsOfUse() {
        start<WebViewActivity>(
            false,
            bundleOf(
                WebViewActivity.WEBVIEW_URL to preference.getPreferenceString(
                    getString(
                        R.string.terms_of_use
                    )
                )
            )
        )
    }

    /**
     * 개인정보 처리방침
     */
    private fun moveToPrivacyPolicy() {
        start<WebViewActivity>(
            false,
            bundleOf(
                WebViewActivity.WEBVIEW_URL to preference.getPreferenceString(
                    getString(
                        R.string.privacy_policy
                    )
                )
            )
        )
    }

    /**
     * 공지사항
     */
    private fun moveToNotice() {
        start<WebViewActivity>(
            false,
            bundleOf(WebViewActivity.WEBVIEW_URL to preference.getPreferenceString(getString(R.string.notice)))
        )
    }

    /**
     * 문의하기
     */
    private fun moveToContact() {
        try {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.google.android.gm")
                putExtra(
                    Intent.EXTRA_EMAIL,
                    resources.getStringArray(R.array.developer_email)
                )
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    resources.getString(R.string.developer_email_subject)
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    "모델명 : ${Build.MODEL}\n" +
                            "OS버전 : ${Build.VERSION.RELEASE}\n" +
                            "SDK버전 : ${Build.VERSION.SDK_INT}\n" +
                            "앱버전 : ${viewModel.appVersion}\n " +
                            "-----------------------------------------\n\n"
                )
                startActivity(this)
            }
        } catch (e: ActivityNotFoundException) {
            showToast(resources.getString(R.string.error))
        }
    }

    /**
     * 릴리즈 노트
     */
    private fun moveToReleaseNote() {
        start<WebViewActivity>(
            false,
            bundleOf(
                WebViewActivity.WEBVIEW_URL to preference.getPreferenceString(
                    getString(
                        R.string.release_note
                    )
                )
            )
        )
    }

    /**
     * 구글 플레이 스토어
     */
    private fun moveToPlayStore() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(resources.getString(R.string.play_store_market))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(resources.getString(R.string.not_installed_play_store))
        }
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java).apply {

            }
        }
    }
}