package com.ddd4.synesthesia.beer.presentation.base

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.presentation.commom.entity.HandleEvent
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.ddd4.synesthesia.beer.util.ext.dateFormat
import com.ddd4.synesthesia.beer.util.ext.showSnackBar
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity(), HandleEvent {

    @Inject
    lateinit var preference: SharedPreferenceProvider

    @Inject
    lateinit var versionManager: VersionManager
    private var backKeyPressedTime = 0L
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            if (this !is MainActivity) {
                finish()
                return
            }
            if (isInTimeInput()) {
                finishAffinity()
            }
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun isInTimeInput(finish: (() -> Unit)? = null): Boolean {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showToast(getString(R.string.back_press))
            return false
        }

        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ?????? ??????????????? ?????? ???
        // ??????????????? ???????????? ????????? ????????? ????????? 2?????? ????????? ???????????? ??????
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish?.invoke()
            return true
        }

        return false
    }

    fun showRecentlyVisitTime() {
        preference.getPreferenceString(getString(R.string.key_recently_visit))?.let {
            when (it.isEmpty()) {
                false -> {
                    dateFormat().run {
                        binding.root.showSnackBar("?????? ????????? : ${it}")
                        versionManager.lastVisitTime = it
                        preference.setValue(
                            getString(R.string.key_recently_visit),
                            format(System.currentTimeMillis())
                        )
                    }
                }
                true -> {
                    dateFormat().run {
                        binding.root.showSnackBar("???????????????")
                        preference.setValue(
                            getString(R.string.key_recently_visit),
                            format(System.currentTimeMillis())
                        )
                    }
                }
            }
        }
    }

    /**
     * ?????? ????????? ?????????
     */
    fun moveToPlayStore() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(resources.getString(R.string.play_store_market))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            showToast(resources.getString(R.string.not_installed_play_store))
        }
    }

    /**
     * ????????? ??????
     */
    open fun initBind() {}

    /**
     * ??????????????? ????????? ????????? ??????
     */
    open fun initObserver() {}

    /**
     * ???????????? ????????? ???????????? ??????
     */
    override fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun handleActionEvent(entity: ActionEntity) {}
}