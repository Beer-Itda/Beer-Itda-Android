package com.hjiee.presentation.ui.common.sort.view

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.LayoutBottomSortBinding
import com.hjiee.presentation.base.BaseBottomSheetDialogFragment
import com.hjiee.presentation.ui.common.sort.viewmodel.SortViewModel
import com.hjiee.presentation.util.ext.updateTypeface
import com.hjiee.presentation.util.sort.SortType
import com.hjiee.core.util.listener.setOnDebounceClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortDialog
    : BaseBottomSheetDialogFragment<LayoutBottomSortBinding>(R.layout.layout_bottom_sort) {

    private val viewModel: SortViewModel by viewModels()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBind()
    }

    override fun initBind() {
        binding.vm = viewModel

        viewModel.sortType.observe(viewLifecycleOwner, Observer {
            updateTypeface(it)
        })

        binding.sortClose.setOnDebounceClickListener {
            this.dismiss()
        }
    }

    private fun updateTypeface(sortType: SortType) {

        val typeBold = ResourcesCompat.getFont(
            requireContext(),
            R.font.notosans_kr_bold
        ) to resources.getColor(R.color.butterscotch, null)

        val typeRegular = ResourcesCompat.getFont(
            requireContext(),
            R.font.notosans_kr_regular
        ) to resources.getColor(R.color.white, null)

        binding.apply {
            sortDefault.updateTypeface(typeRegular)
            sortRating.updateTypeface(typeRegular)
            sortReview.updateTypeface(typeRegular)

            when (sortType) {
                SortType.Default -> sortDefault.updateTypeface(typeBold)
                SortType.Rating -> sortRating.updateTypeface(typeBold)
                SortType.Review -> sortReview.updateTypeface(typeBold)
            }
        }
    }

    override fun initObserving() {

    }
}