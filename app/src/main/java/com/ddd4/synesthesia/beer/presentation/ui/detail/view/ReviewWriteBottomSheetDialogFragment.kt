package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.ReviewWriteActionEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.ReviewWriteClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.ReviewWriteViewModel
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.level.view.LevelGuideActivity
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.ddd4.synesthesia.beer.util.ext.start
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.*
import com.hjiee.core.util.listener.setOnDebounceClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReviewWriteBottomSheetDialogFragment :
    BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {

    private val viewModel by viewModels<ReviewWriteViewModel>()

    private val beerId by lazy { arguments?.getInt(KEY_BEER_ID) }
    private val reviewContent by lazy { arguments?.getString(KEY_REVIEW_CONTENT).orEmpty() }
    private val reviewStar by lazy { arguments?.getFloat(KEY_REVIEW_STAR).orDefault(0.5f) }
    private val isModify by lazy { arguments?.getBoolean(KEY_IS_MODIFY).orFalse() }

    private var callbackListener: (() -> Unit)? = null
    private val minRating = 0.5f
    private var isSuccess = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBind()
        initObserving()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                    notSavedCheck()
                    true
                } else {
                    false
                }
            }

            setOnShowListener { dialog ->
                (dialog as? BottomSheetDialog)?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.run {
                        with(BottomSheetBehavior.from(this)) {
                            state = BottomSheetBehavior.STATE_EXPANDED
                            isDraggable = false
                            isHideable = false
                            isCancelable = false
                        }
                    }
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun initBind() {
        binding.apply {
            viewModel = this@ReviewWriteBottomSheetDialogFragment.viewModel
            isModify = this@ReviewWriteBottomSheetDialogFragment.isModify

            ivClose.setOnDebounceClickListener { notSavedCheck() }
        }
    }

    override fun initObserving() {
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
        observeHandledEvent(viewModel.event.throwable) {
            if (it.first.message.isNullOrEmpty().not()) {
                context?.showToast(it.first.message.orEmpty())
            }
        }
        viewModel.starScore.observe(viewLifecycleOwner, Observer {
            it?.let { rating ->
                if (rating < minRating) {
                    viewModel.starScore.value = minRating
                }
            } ?: kotlin.run { viewModel.starScore.value = 0.5f }
        })
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            ReviewWriteClickEntity.LevelGuide -> {
                start<LevelGuideActivity>()
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is ReviewWriteActionEntity.Success -> {
                isSuccess = true
                context?.showToast(getString(R.string.success_registered_review))
                dismiss()
            }
        }
    }

    fun setCallbackListener(callback: () -> Unit) {
        callbackListener = callback
    }

    private fun notSavedCheck() {
        if (viewModel.isReviewChanged()) {
            dismiss()
        } else {
            showDialog()
        }
    }

    private fun showDialog() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.page_out))
                .setMessage(getString(R.string.do_not_save))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    dismiss()
                }
                .setNegativeButton(getString(R.string.no), null)
                .show()
        }
    }

    companion object {
        const val KEY_REVIEW_CONTENT = "review_content"
        const val KEY_REVIEW_STAR = "review_star"
        const val KEY_IS_MODIFY = "is_modify"


        fun getBundle(
            beerId: Int,
            content: String? = null,
            star: Float? = null,
            isModify: Boolean? = null
        ): Bundle {
            return Bundle().apply {
                putInt(KEY_BEER_ID, beerId)
                putString(KEY_REVIEW_CONTENT, content)
                putFloat(KEY_REVIEW_STAR, star.orDefault(0.5f))
                putBoolean(KEY_IS_MODIFY, isModify.orFalse())
            }
        }
    }
}