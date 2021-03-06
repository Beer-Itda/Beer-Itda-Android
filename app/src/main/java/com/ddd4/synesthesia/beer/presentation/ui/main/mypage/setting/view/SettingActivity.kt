package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.ThemeHelper
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.databinding.ActivityMyPageSettingBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.delete.view.MyAccountDeleteActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.viewmodel.SettingViewModel
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity :
    BaseActivity<ActivityMyPageSettingBinding>(R.layout.activity_my_page_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val settingAdapter by lazy { SettingAdapter() }
    private val themeList by lazy { resources.getStringArray(R.array.theme_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.init()
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
        observeHandledEvent(viewModel.event.throwable) {
            if (it.first.message.isNullOrEmpty().not()) {
                showToast(it.first.message.orEmpty())
            }
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
                start<LoginActivity>(LoginActivity.getTaskIntent(this))
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
            // ????????????
            InfomationsData.NOTICE.title -> {
                moveToNotice()
            }
            // ????????????
            InfomationsData.CONTACT.title -> {
                moveToContact()
            }
            // ????????? ??????
            InfomationsData.RELEASE_NOTE.title -> {
                moveToReleaseNote()
            }
            // ???????????? ???????????????
            InfomationsData.OPEN_SOURCE_LIB.title -> {
                start<OssLicensesMenuActivity>()
            }
            // ????????? ????????? ??????
            InfomationsData.PLAY_STORE.title -> {
                moveToPlayStore()
            }
            // ????????????????????????
            InfomationsData.PRIVACY_POLICY.title -> {
                moveToPrivacyPolicy()
            }
            // ????????????
            InfomationsData.TERMS_OF_USE.title -> {
                moveToTermsOfUse()
            }
            // ????????????
            InfomationsData.LOGOUT.title -> {
                showDialog()
            }
            // ????????????
            InfomationsData.UNLINK.title -> {
                start<MyAccountDeleteActivity>()
            }
            // ????????????
            InfomationsData.PUSH.title -> {
                showToast(resources.getString(R.string.please_wait_for_a_little_while))
            }
            // ????????????
            InfomationsData.THEME.title -> {
                MaterialAlertDialogBuilder(this@SettingActivity, R.style.Dialog)
                    .setTitle(getString(R.string.setting_theme))
                    .setSingleChoiceItems(themeList, viewModel.getSelectedThemePosition()) { dialog, position ->
                        viewModel.saveTheme(position)
                        ThemeHelper.getThemeMode(position).let {
                            ThemeHelper.applyTheme(it)
                        }
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this@SettingActivity)
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.logOut()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    /**
     * ????????????
     */
    private fun moveToTermsOfUse() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(R.string.terms_of_use))
            )
        )
    }

    /**
     * ???????????? ????????????
     */
    private fun moveToPrivacyPolicy() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(R.string.privacy_policy))
            )
        )
    }

    /**
     * ????????????
     */
    private fun moveToNotice() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(R.string.notice))
            )
        )
    }

    /**
     * ????????????
     */
    private fun moveToContact() {
        start<WebViewActivity>(
            WebViewActivity.getInquiryIntent(
                context = this
            )
        )
    }

    /**
     * ????????? ??????
     */
    private fun moveToReleaseNote() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(R.string.release_note))
            )
        )
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java).apply {

            }
        }
    }
}