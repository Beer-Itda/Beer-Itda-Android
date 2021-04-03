package com.ddd4.synesthesia.beer.presentation.ui.filter.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.model.filter.BeerLargeType
import com.ddd4.synesthesia.beer.databinding.ActivityFilterBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.entity.FilterActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.entity.FilterClicklEntity
import com.ddd4.synesthesia.beer.presentation.ui.filter.item.middle.StyleMiddleItemViewHolder
import com.ddd4.synesthesia.beer.presentation.ui.filter.view.adapter.StyleMiddleListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.view.adapter.StyleSelectedListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.view.adapter.StyleSmallListAdapter
import com.ddd4.synesthesia.beer.presentation.ui.filter.viewmodel.FilterViewModel
import com.google.android.material.tabs.TabLayout
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter),
    DiscreteScrollView.OnItemChangedListener<StyleMiddleItemViewHolder>,
    TabLayout.OnTabSelectedListener {

    private val viewModel: FilterViewModel by viewModels()
    private val filterSetAdapter by lazy { StyleSmallListAdapter() }
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
            vm = viewModel
            with(rvFilterMiddleCategory) {
                adapter = middleCategoryListAdapter
                lifecycleOwner = this@FilterActivity
                addOnItemChangedListener(this@FilterActivity)
                setItemTransformer(
                    ScaleTransformer.Builder()
                        .setMinScale(0.7f)
                        .build()
                )
                setOffscreenItems(3)
                scrollToPosition(0)
            }
            with(rvFilterSet) {
                adapter = filterSetAdapter
                lifecycleOwner = this@FilterActivity
            }
            with(rvSelectedStyle) {
                adapter = selectedStyleAdapter
                lifecycleOwner = this@FilterActivity
            }
            with(tabs) {
                addTab(newTab().setText(BeerLargeType.Ale.name))
                addTab(newTab().setText(BeerLargeType.Lager.name))
                addTab(newTab().setText(BeerLargeType.Lambic.name))
                addTab(newTab().setText(BeerLargeType.Etc.name))
                addOnTabSelectedListener(this@FilterActivity)
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
            is FilterActionEntity.UpdateList -> {
                middleCategoryListAdapter.addAll(entity.styleMiddle, true)
            }
            is FilterActionEntity.UpdateStyleSet -> {
                filterSetAdapter.addAll(entity.styleSmall, true)
            }
            is FilterActionEntity.UpdateSelectedStyleList -> {
                selectedStyleAdapter.addAll(entity.style, true)
                binding.rvSelectedStyle.scrollToPosition(0)
            }
            is FilterActionEntity.ShowToast -> {
                Toast.makeText(this, entity.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is FilterClicklEntity.SelectMiddleCategory -> {
            }
            is FilterClicklEntity.SelectStyleSet -> {
            }
            is FilterClicklEntity.SelectDone -> {
                // TODO style 저장 하기
                entity.selectItem.map {

                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}
    override fun onTabReselected(tab: TabLayout.Tab?) {}
    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                viewModel.load(BeerLargeType.Ale)
            }
            1 -> {
                viewModel.load(BeerLargeType.Lager)
            }
            2 -> {
                viewModel.load(BeerLargeType.Lambic)
            }
            3 -> {
                viewModel.load(BeerLargeType.Etc)
            }
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
                            FilterActivity::class.java
                        ).apply {
                        }, requestCode
                    )
                }
                else -> {
                    context.startActivity(Intent(context, FilterActivity::class.java).apply {
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
                    FilterActivity::class.java
                ).apply {
                }, requestCode
            )
        }
    }
}