package com.hjiee.presentation.ui.main.home.main.item.parent.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ItemHomeBeerHorizontalListBinding
import com.hjiee.presentation.ui.main.home.main.item.HomeViewHolder
import com.hjiee.presentation.ui.main.home.main.view.HomeListChildAdapter
import com.hjiee.presentation.util.ext.createView
import com.hjiee.presentation.util.listener.EndlessRecyclerViewScrollListener

class BeerListItemViewHolder(
    itemView: View
) : HomeViewHolder<BeerListItemViewModel, ItemHomeBeerHorizontalListBinding>(itemView) {

    val adapter = HomeListChildAdapter()
    private var endlessRecyclerOnScrollListener: EndlessRecyclerViewScrollListener? = null

    companion object {
        fun newInstance(parent: ViewGroup) =
            BeerListItemViewHolder(parent.createView(R.layout.item_home_beer_horizontal_list))
    }

    override fun onBind(viewModel: BeerListItemViewModel, position: Int) {
        endlessRecyclerOnScrollListener =
            object : EndlessRecyclerViewScrollListener(binding?.rvBeers?.layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadMore()
                }
            }

        binding?.run {
            item = viewModel
            with(rvBeers) {
                adapter = this@BeerListItemViewHolder.adapter
                endlessRecyclerOnScrollListener?.let {
                    removeOnScrollListener(it)
                    addOnScrollListener(it)
                }
            }
            executePendingBindings()
        }
        adapter.clear()
        adapter.addAll(viewModel.beers)
    }
}