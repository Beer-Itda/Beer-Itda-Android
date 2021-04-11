package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.HomeNavigationDirections
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.entity.MyPageClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity.Companion.WEBVIEW_URL
import com.ddd4.synesthesia.beer.util.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {


    @Inject
    lateinit var appConfig: AppConfig
    private val myPageViewModel by viewModels<MyPageViewModel>()
    private val nickNameCallback = object : SimpleCallback {
        override fun call(text: String) {
            binding.tvName.text = text
            myPageViewModel.updateUserInfo(text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SimpleCallback.callback = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()
        SimpleCallback.callback = nickNameCallback


    }

    override fun initBind() {
        binding.apply {
            vm = myPageViewModel
            userAdapter = ItemsAdapter(
                R.layout.layout_my_page,
                BR.my
            ).apply { updateItems(myPageViewModel.generateInfoList()) }
        }
    }

    override fun initObserver() {
        myPageViewModel.isUnConnected.observe(viewLifecycleOwner, Observer {
            if (it) {
                preference.clear()
                LoginActivity.start(requireContext(), getString(R.string.success_logout))
            }
        })
        observeHandledEvent(myPageViewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is MyPageClickEntity.SelectItem -> {
                infomationsEvent(entity.info.title)
            }
            is MyPageClickEntity.Modify -> {
                NickNameActivity.start(requireContext(), binding.tvName.text.toString())
            }
        }
    }

    private fun infomationsEvent(section: String) {
        when (section) {
            // 내가 남긴 리뷰
            InfomationsData.REVIEW.title -> {
                findNavController().navigate(HomeNavigationDirections.actionToMyReview())
            }
            // 내가 찜한 맥주
            InfomationsData.FAVORITE.title -> {
                findNavController().navigate(HomeNavigationDirections.actionToMyFavorite())
            }
            // 공지사항
            InfomationsData.NOTICE.title -> {
                start<WebViewActivity>(
                    false,
                    bundleOf(WEBVIEW_URL to preference.getPreferenceString(getString(R.string.notice)))
                )
            }
            // 문의하기
            InfomationsData.CONTACT.title -> {
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
                                    "앱버전 : ${appConfig.version}\n " +
                                    "-----------------------------------------\n\n"
                        )
                        startActivity(this)
                    }
                } catch (e: ActivityNotFoundException) {
                    context?.showToast(resources.getString(R.string.error))
                }
            }
            // 릴리즈 노트
            InfomationsData.RELEASE_NOTE.title -> {
                start<WebViewActivity>(
                    false,
                    bundleOf(WEBVIEW_URL to preference.getPreferenceString(getString(R.string.release_note)))
                )
            }
            // 오픈소스 라이브러리
            InfomationsData.OPEN_SOURCE_LIB.title -> {
                start<OssLicensesMenuActivity>(false)
            }
            // 플레이 스토어 평가
            InfomationsData.PLAY_STORE.title -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(resources.getString(R.string.play_store_market))
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    context?.showToast(resources.getString(R.string.not_installed_play_store))
                }
            }
            // 개인정보처리방침
            InfomationsData.PRIVACY_POLICY.title -> {
                start<WebViewActivity>(
                    false,
                    bundleOf(WEBVIEW_URL to preference.getPreferenceString(getString(R.string.privacy_policy)))
                )
            }
            InfomationsData.TERMS_OF_USE.title -> {
                start<WebViewActivity>(
                    false,
                    bundleOf(WEBVIEW_URL to preference.getPreferenceString(getString(R.string.terms_of_use)))
                )
            }
            // 로그아웃
            InfomationsData.LOGOUT.title -> {
                unConnected(getString(R.string.logout_message))
            }
            // 회원탈퇴
            InfomationsData.UNLINK.title -> {
                unConnected(getString(R.string.unlink_message))
            }
            InfomationsData.PUSH.title -> {
                context?.showToast(resources.getString(R.string.please_wait_for_a_little_while))
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
                        myPageViewModel.logout()
                    }
                    getString(R.string.unlink_message) -> {
                        myPageViewModel.unlink()
                    }
                }
            }
        ).show(parentFragmentManager, null)
    }
}