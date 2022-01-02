package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityLevelGuideBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelGuideActivity : BaseActivity<ActivityLevelGuideBinding>(R.layout.activity_level_guide) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
    }

    override fun initBind() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
        }
    }

    companion object {

        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, LevelGuideActivity::class.java).apply {

            }
        }
    }

}