package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.ext.showSimpleDialog
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_global_toolbar.view.*

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {


    private val myPageViewModel by viewModels<MyPageViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when ((item as? MyInfo)?.type) {
                    InfomationsType.ITEM -> { infomationsEvent(item.title) }
                    InfomationsType.LOGOUT -> { logoutEvent() }
                    else -> { /*Nothing*/ }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            userAdapter = BaseItemsApdater(R.layout.layout_my_page, BR.my, itemClickListener).apply { updateItems(myPageViewModel.generateInfoList()) }
            inclideToolbar.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun infomationsEvent(section: String) {
        context?.showToast(section)
        when (section) {
            InfomationsData.ACTIVE.title -> {

            }
            InfomationsData.STAR.title -> {

            }
            InfomationsData.REVIEW.title -> {

            }
            InfomationsData.HELP.title -> {

            }
            InfomationsData.NOTICE.title -> {

            }
            InfomationsData.CONTACT.title -> {

            }
            InfomationsData.SERVICE_INFO.title -> {

            }
            InfomationsData.TERMS_OF_USE.title -> {

            }
            InfomationsData.SETTING.title -> {

            }
            InfomationsData.PUSH.title -> {

            }
            InfomationsData.LOGOUT.title -> {

            }
        }
    }

    private fun logoutEvent() {
        context?.showSimpleDialog(message = getString(R.string.logout_message)) {
            myPageViewModel.logout {
                if (it) {
                    preference.clear()
                    start<LoginActivity>(true, bundleOf(Pair(getString(R.string.is_show_snackbar), getString(R.string.success_logout))))
                }
            }
        }
    }
}