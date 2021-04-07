package com.ddd4.synesthesia.beer.presentation.ui.filter.style.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityFilterStyleBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.aroma.view.AromaActivity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.entity.StyleClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle.StyleMiddleItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.adapter.StyleMiddleListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.adapter.StyleSelectedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.view.adapter.StyleSmallListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.viewmodel.StyleViewModel
import com.google.android.material.tabs.TabLayout
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StyleActivity : BaseActivity<ActivityFilterStyleBinding>(R.layout.activity_filter_style),
    DiscreteScrollView.OnItemChangedListener<StyleMiddleItemViewHolder>,
    TabLayout.OnTabSelectedListener {

    private val viewModel: StyleViewModel by viewModels()
    private val smallCategoryListAdapter by lazy { StyleSmallListAdapter() }
    private val middleCategoryListAdapter by lazy { StyleMiddleListAdapter() }
    private val selectedStyleAdapter by lazy { StyleSelectedListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.init()
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@StyleActivity.viewModel
            with(rvFilterMiddleCategory) {
                adapter = middleCategoryListAdapter
                lifecycleOwner = this@StyleActivity
                addOnItemChangedListener(this@StyleActivity)
                setItemTransformer(
                    ScaleTransformer.Builder()
                        .setMinScale(0.7f)
                        .build()
                )
                setOffscreenItems(3)
                scrollToPosition(0)
            }
            with(rvFilterStyleSmall) {
                adapter = smallCategoryListAdapter
                lifecycleOwner = this@StyleActivity
            }
            with(rvSelectedStyle) {
                adapter = selectedStyleAdapter
                lifecycleOwner = this@StyleActivity
            }
            with(includeToolbar.toolbar) {
                setOnClickListener {
                    finish()
                }
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
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is StyleActionEntity.UpdateLarge -> {
                with(binding.tabs) {
                    entity.styleLarge.forEach {
                        addTab(newTab().setText(it.bigName))
                    }
                    addOnTabSelectedListener(this@StyleActivity)
                }
            }
            is StyleActionEntity.UpdateMiddle -> {
                middleCategoryListAdapter.addAll(entity.styleMiddle, true)
            }
            is StyleActionEntity.UpdateSmall -> {
                smallCategoryListAdapter.addAll(entity.styleSmall, true)
            }
            is StyleActionEntity.UpdateSelectedStyleList -> {
                selectedStyleAdapter.addAll(entity.style, true)
                binding.rvSelectedStyle.scrollToPosition(0)
            }
            is StyleActionEntity.ShowToast -> {
                Toast.makeText(this, entity.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is StyleClicklEntity.SelectMiddleCategory -> {
            }
            is StyleClicklEntity.SelectStyleSet -> {
            }
            is StyleClicklEntity.SelectDone -> {
                // TODO style 저장 하기
                entity.selectItem.map {

                }
                AromaActivity.start(this@StyleActivity)
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.position?.let { position ->
            viewModel.load(position)
        }
    }

    override fun onCurrentItemChanged(
        viewHolder: StyleMiddleItemViewHolder?,
        adapterPosition: Int
    ) {
        viewModel.loadFilterSet(adapterPosition)
    }

    companion object {
        const val REQ_CODE_FILTER = 30

        @JvmStatic
        fun start(
            context: Context,
            requestCode: Int = REQ_CODE_FILTER
        ) {
            when (context) {
                is Activity -> {
                    context.startActivityForResult(
                        Intent(
                            context,
                            StyleActivity::class.java
                        ).apply {
                        }, requestCode
                    )
                }
                else -> {
                    context.startActivity(Intent(context, StyleActivity::class.java).apply {
                    })
                }
            }
        }

        @JvmStatic
        fun start(
            fragment: Fragment,
            requestCode: Int = REQ_CODE_FILTER
        ) {
            fragment.startActivityForResult(
                Intent(
                    fragment.context,
                    StyleActivity::class.java
                ).apply {
                }, requestCode
            )
        }
    }
}