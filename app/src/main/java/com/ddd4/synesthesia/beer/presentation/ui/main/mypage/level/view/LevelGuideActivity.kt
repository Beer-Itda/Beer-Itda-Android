package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityLevelGuideBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.model.LevelActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.viewmodel.LevelGuideViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.hjiee.core.event.entity.ActionEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelGuideActivity : BaseActivity<ActivityLevelGuideBinding>(R.layout.activity_level_guide) {

    private val viewModel by viewModels<LevelGuideViewModel>()
    private val listAdapter by lazy { LevelAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.load()
    }

    override fun initBind() {
        binding.apply {
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvLevelGuide) {
                adapter = listAdapter
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is LevelActionEntity.UpdateUi -> {
                listAdapter.clear()
                listAdapter.addAll(entity.items)
            }
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