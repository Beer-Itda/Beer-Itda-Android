package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.LayoutBottomStarRatingBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseBottomSheetDialogFragment
import com.ddd4.synesthesia.beer.presentation.ui.detail.entity.StarRatingBottomClickEntity
import com.ddd4.synesthesia.beer.presentation.ui.detail.viewmodel.StarRatingViewModel
import com.ddd4.synesthesia.beer.util.CustomAlertDialog
import com.ddd4.synesthesia.beer.util.KEY_BEER_ID
import com.ddd4.synesthesia.beer.util.SimpleCallback
import com.ddd4.synesthesia.beer.util.ext.observeHandledEvent
import com.ddd4.synesthesia.beer.util.ext.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.ext.*
import com.hjiee.core.util.listener.setOnDebounceClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StarRatingBottomDialogFragment :
    BaseBottomSheetDialogFragment<LayoutBottomStarRatingBinding>(R.layout.layout_bottom_star_rating) {

    private val starRatingViewModel by viewModels<StarRatingViewModel>()

    private val beerId by lazy { arguments?.getInt(KEY_BEER_ID) }
    private val reviewContent by lazy { arguments?.getString(KEY_REVIEW_CONTENT).orEmpty() }
    private val reviewRatio by lazy { arguments?.getFloat(KEY_REVIEW_RATIO).orDefault(0.5f) }
    private val isFirstWrite by lazy { arguments?.getBoolean(KEY_IS_FIRST_WRITE).orFalse() }

    private var dismiss: (() -> Unit)? = null
    private val minRating = 0.5f

    private val reviewCallback = object : SimpleCallback {
        override fun call(text: String) {
            starRatingViewModel.review.value = text
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SimpleCallback.callback = reviewCallback
        isCancelable = false
        binding.apply {
            vm = starRatingViewModel
            reviewContent = this@StarRatingBottomDialogFragment.reviewContent
            reviewRatio = this@StarRatingBottomDialogFragment.reviewRatio
            isFirstWrite = this@StarRatingBottomDialogFragment.isFirstWrite


            btnSendReview.setOnDebounceClickListener {
                beerId?.let { id ->
                    starRatingViewModel.postReview(id)
                } ?: kotlin.run { context?.showToast(getString(R.string.review_fail_message)) }
            }
            ivClose.setOnDebounceClickListener {
                notice()
            }
        }
        initObserving()
        starRatingViewModel.setData(
            reviewContent = reviewContent,
            reviewRatio = reviewRatio
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val reviewSheetDialog = BottomSheetDialog(requireContext(), theme)
        reviewSheetDialog.setOnShowListener { dialog ->
            (dialog as? BottomSheetDialog)?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?.run {
                    with(BottomSheetBehavior.from(this)) {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        isDraggable = false
                        isHideable = false
                    }
                }
        }

        return reviewSheetDialog

    }

    override fun onDestroy() {
        super.onDestroy()
        SimpleCallback.callback = null
    }

    fun showDialog(fm: FragmentManager, dismiss: (() -> Unit)?) {
        show(fm, "")
        this@StarRatingBottomDialogFragment.dismiss = dismiss
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogStyle
    }

    override fun initBind() {

    }

    override fun initObserving() {
        observeHandledEvent(starRatingViewModel.event.select) {
            handleSelectEvent(it)
        }
        starRatingViewModel.register.observe(viewLifecycleOwner, Observer {
            dismiss()
            dismiss?.invoke()
        })
        starRatingViewModel.rating.observe(viewLifecycleOwner, Observer {
            it?.let { rating ->
                if (rating < minRating) {
                    starRatingViewModel.rating.value = minRating
                }
            } ?: kotlin.run { starRatingViewModel.rating.value = 0.5f }
        })
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            StarRatingBottomClickEntity.ClickGuide -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_wait_for_a_little_while),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
    }

    private fun notice() {
        if (reviewRatio == starRatingViewModel.rating.value && reviewContent == starRatingViewModel.review.value) {
            dismiss()
        } else {
            CustomAlertDialog(
                title = getString(R.string.page_out),
                message = getString(R.string.do_not_save),
                posivie = getString(R.string.yes),
                negative = getString(R.string.no),
                result = DialogInterface.OnClickListener { dialog, which -> dismiss() }
            ).show(parentFragmentManager, null)
        }
    }

    companion object {
        const val KEY_REVIEW_CONTENT = "review_content"
        const val KEY_REVIEW_RATIO = "review_ratio"
        const val KEY_IS_FIRST_WRITE = "is_first_write"


        fun getBundle(
            beerId: Int,
            reviewContent: String? = null,
            reviewRatio: Float? = null,
            isFirstWrite: Boolean? = null
        ): Bundle {
            return Bundle().apply {
                putInt(KEY_BEER_ID, beerId)
                putString(KEY_REVIEW_CONTENT, reviewContent)
                putFloat(KEY_REVIEW_RATIO, reviewRatio.orDefault(0.5f))
                putBoolean(KEY_IS_FIRST_WRITE, isFirstWrite.orFalse())
            }
        }
    }
}