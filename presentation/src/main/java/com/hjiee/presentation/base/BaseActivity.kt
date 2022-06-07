package com.hjiee.presentation.base

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hjiee.presentation.commom.entity.HandleEvent
import com.hjiee.presentation.ui.main.view.MainActivity
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.presentation.R
import com.hjiee.presentation.util.ext.dateFormat
import com.hjiee.presentation.util.ext.showSnackBar
import com.hjiee.presentation.util.ext.showToast
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

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
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
                        binding.root.showSnackBar("최근 접속일 : ${it}")
                        versionManager.lastVisitTime = it
                        preference.setValue(
                            getString(R.string.key_recently_visit),
                            format(System.currentTimeMillis())
                        )
                    }
                }
                true -> {
                    dateFormat().run {
                        binding.root.showSnackBar("환영합니다")
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
     * 구글 플레이 스토어
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
     * 초기화 로직
     */
    open fun initBind() {}

    /**
     * 뷰모델에서 옵저빙 초기화 로직
     */
    open fun initObserver() {}

    /**
     * 옵저빙한 로직을 처리하는 로직
     */
    override fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun handleActionEvent(entity: ActionEntity) {}
}