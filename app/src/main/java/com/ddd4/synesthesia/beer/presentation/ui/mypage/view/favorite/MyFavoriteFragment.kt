package com.ddd4.synesthesia.beer.presentation.ui.mypage.view.favorite

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.FragmentMyFavoriteBinding
import com.ddd4.synesthesia.beer.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.BeerClickEntity
import com.ddd4.synesthesia.beer.presentation.commom.adapter.ItemsAdapter
import com.ddd4.synesthesia.beer.presentation.ui.detail.view.DetailActivity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel.favorite.MyFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavoriteFragment : BaseFragment<FragmentMyFavoriteBinding>(R.layout.fragment_my_favorite) {

    private val viewModel by viewModels<MyFavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserver()
    }

    override fun initBind() {
        binding.apply {
            adapter = ItemsAdapter(R.layout.item_my_favorite,BR.beer)
            includeToolbar.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initObserver() {
        viewModel.myFavorites.observe(viewLifecycleOwner, Observer {
            binding.adapter?.updateItems(it)
        })
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when(entity) {
            is BeerClickEntity.SelectItem -> {
                DetailActivity.start(requireContext(), entity.beer.id)
            }
        }
    }
}