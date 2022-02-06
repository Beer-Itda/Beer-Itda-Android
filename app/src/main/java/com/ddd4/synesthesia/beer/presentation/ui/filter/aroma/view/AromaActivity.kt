package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityFilterAromaBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaSelectedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel.AromaViewModel
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.StyleActivity
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AromaActivity : BaseActivity<ActivityFilterAromaBinding>(R.layout.activity_filter_aroma) {

    private val viewModel by viewModels<AromaViewModel>()
    private val listAdapter by lazy { AromaListAdapter() }
    private val selectedListAdapter by lazy { AromaSelectedListAdapter() }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                finish()
            }
        }

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
                adapter = listAdapter
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
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.throwable) {
            if(it.first.message.isNullOrEmpty().not()) {
                Toast.makeText(this@AromaActivity, it.first.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is AromaClickEntity.Skip -> {
                start<StyleActivity>(activityLauncher = activityLauncher)
//                activityLauncher.launch(StyleActivity.getIntent(this))
            }
            is AromaClickEntity.SelectDone -> {
                start<StyleActivity>(activityLauncher = activityLauncher)
//                activityLauncher.launch(StyleActivity.getIntent(this))
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is AromaActionEntity.ShowToast -> {
                showToast(entity.message)
            }
            is AromaActionEntity.UpdateList -> {
                listAdapter.clear()
                listAdapter.addAll(entity.list, true)
            }
            is AromaActionEntity.UpdateSelectedList -> {
                selectedListAdapter.addAll(entity.list, false)
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