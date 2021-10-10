package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityMyFavoriteBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.commom.entity.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.BeerDetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.favorite.viewmodel.MyFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * 내가 찜한 맥주 리스트
 */
@AndroidEntryPoint
class MyFavoriteActivity : BaseActivity<ActivityMyFavoriteBinding>(R.layout.activity_my_favorite) {

    private val viewModel by viewModels<MyFavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            vm = viewModel
            adapter = ItemsAdapter(R.layout.item_my_favorite, BR.beer)
            includeToolbar.toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun initObserver() {
        viewModel.myFavorites.observe(this@MyFavoriteActivity, Observer {
            binding.adapter?.updateItems(it)
        })
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is BeerClickEntity.SelectItem -> {
                start(intent = BeerDetailActivity.getIntent(this, entity.beer.id))
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