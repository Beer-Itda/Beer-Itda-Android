package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityFilterAromaBinding
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.hjiee.core.event.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaSelectedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel.AromaViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AromaActivity : BaseActivity<ActivityFilterAromaBinding>(R.layout.activity_filter_aroma) {

    private val viewModel by viewModels<AromaViewModel>()
    private val aromaListAdapter by lazy { AromaListAdapter() }
    private val selectedListAdapter by lazy { AromaSelectedListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.load()
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@AromaActivity.viewModel
            with(includeToolbar.toolbar) {
                setOnClickListener { finish() }
            }
            with(rvFilterAroma) {
                adapter = aromaListAdapter
            }
            with(rvSelectedAroma) {
                adapter = selectedListAdapter
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
            is AromaActionEntity.ShowToast -> {
                Toast.makeText(this@AromaActivity, entity.message, Toast.LENGTH_SHORT).show()
            }
            is AromaActionEntity.UpdateList -> {
                aromaListAdapter.addAll(entity.list, true)
            }
            is AromaActionEntity.UpdateSelectedList -> {
                selectedListAdapter.addAll(entity.list, true)
            }
            is AromaActionEntity.SelectDone -> {
                start(
                    intent = StyleActivity.getIntent(this),
                    isFinish = true
                )
            }
        }
    }

    companion object {
        const val REQ_CODE_AROMA = 302

        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, AromaActivity::class.java).apply {
            }
        }
    }
}