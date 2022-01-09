package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMyFavoriteBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.model.MyFavoriteActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.viewmodel.MyFavoriteViewModel
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import dagger.hilt.android.AndroidEntryPoint

/**
 * 내가 찜한 맥주 리스트
 */
@AndroidEntryPoint
class MyFavoriteActivity : BaseActivity<ActivityMyFavoriteBinding>(R.layout.activity_my_favorite) {

    private val viewModel by viewModels<MyFavoriteViewModel>()
    private val adapter by lazy { MyFavoriteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvMyFavorite) {
                adapter = this@MyFavoriteActivity.adapter
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is MyFavoriteActionEntity.UpdateUi -> {
                adapter.clear()
                adapter.addAll(entity.viewModel)
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.ClickItem -> {
                start<BeerDetailActivity>(
                    intent = BeerDetailActivity.getIntent(
                        this,
                        entity.beer.id
                    )
                )
            }
        }
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MyFavoriteActivity::class.java).apply {

            }
        }
    }
}