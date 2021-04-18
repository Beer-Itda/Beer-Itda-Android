package com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityFilterAromaBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.entity.AromaActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.adapter.AromaSelectedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.viewmodel.AromaViewModel
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
                finish()
            }
        }
    }

    companion object {
        const val REQ_CODE_AROMA = 302

        @JvmStatic
        fun start(
            context: Context,
            requestCode: Int = REQ_CODE_AROMA
        ) {
            when (context) {
                is Activity -> {
                    context.startActivityForResult(
                        Intent(
                            context,
                            AromaActivity::class.java
                        ).apply {
                        }, requestCode
                    )
                }
                else -> {
                    context.startActivity(Intent(context, AromaActivity::class.java).apply {
                    })
                }
            }
        }

        @JvmStatic
        fun start(
            fragment: Fragment,
            requestCode: Int = REQ_CODE_AROMA
        ) {
            fragment.startActivityForResult(
                Intent(
                    fragment.context,
                    AromaActivity::class.java
                ).apply {
                }, requestCode
            )
        }
    }
}