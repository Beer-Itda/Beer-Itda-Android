package com.hjiee.presentation.ui.detail.view

import android.content.Context
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.LayoutBottomSheetReviewEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hjiee.core.util.listener.setOnDebounceClickListener

class ReviewEditBottomSheetDialog(
    context: Context,
    private val modifyAction: () -> Unit,
    private val deleteAction: () -> Unit
) : BottomSheetDialog(context, R.style.BottomSheetDialogStyle) {

    val binding by lazy {
        LayoutBottomSheetReviewEditBinding.inflate(layoutInflater).apply {
            tvModify.setOnDebounceClickListener {
                modifyAction.invoke()
                dismiss()
            }
            tvDelete.setOnDebounceClickListener {
                deleteAction.invoke()
                dismiss()
            }
        }
    }

    init {
        setContentView(binding.root)
    }
}