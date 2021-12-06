package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.util.ext.dateFormat
import com.ddd4.synesthesia.beer.util.ext.showSnackBar
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.entity.HandleEvent
import com.ddd4.synesthesia.beer.presentation.ui.main.view.MainActivity
import com.hjiee.core.AppInfo
import com.hjiee.core.provider.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding>(
    private val layoutId: Int
) : AppCompatActivity(), HandleEvent {

    @Inject
    lateinit var preference: SharedPreferenceProvider

    @Inject
    lateinit var appInfo: AppInfo
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
                        appInfo.lastVisitTime = it
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

    open fun initBind() {}
    open fun initObserver() {}

    override fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun handleActionEvent(entity: ActionEntity) {}
}