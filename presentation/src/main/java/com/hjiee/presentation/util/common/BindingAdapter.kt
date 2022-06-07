package com.hjiee.presentation.util.common

import android.animation.LayoutTransition
import android.app.Activity
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hjiee.presentation.R
import com.hjiee.presentation.commom.entity.ThrowEntity
import com.hjiee.presentation.util.NetworkStatus
import com.hjiee.presentation.util.decoration.HorizontalItemSpaceDecoration
import com.hjiee.presentation.util.decoration.RecyclerItemDecoration
import com.hjiee.presentation.util.decoration.VerticalItemSpaceDecoration
import com.hjiee.presentation.util.ext.setVisible
import com.hjiee.presentation.util.ext.showNoticeDialog
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.hjiee.core.ext.dp
import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero
import com.hjiee.core.ext.toPx


@BindingAdapter(value = ["addChip"], requireAll = false)
fun makeChips(chipGroup: ChipGroup, flavor: List<String>?) {
    if (flavor == null) return
    chipGroup.removeAllViews()
    if (flavor.first().isEmpty()) chipGroup.visibility = View.GONE else chipGroup.visibility =
        View.VISIBLE

    flavor.asSequence().forEach {
        val chip = Chip(chipGroup.context).apply {
            setChipDrawable(ChipDrawable.createFromResource(this.context, R.xml.chip_home_item))
            setTextColor(resources.getColor(R.color.black, null))
            ensureAccessibleTouchTarget(20.dp)
            textSize = 12f
            typeface = ResourcesCompat.getFont(chipGroup.context, R.font.notosans_kr_bold)
            text = it
        }
        chipGroup.addView(chip)
    }
}

@BindingAdapter(value = ["space", "vertical_space", "horizontal_space"], requireAll = false)
@Deprecated("insted of setHorizontalItemDecoration or setVerticalItemDecoration")
fun decoration(
    recyclerview: RecyclerView,
    space: Int?,
    verticalSpace: Int?,
    horizontalSpace: Int?
) {
    verticalSpace?.let {
        RecyclerItemDecoration(
            space = space?.toFloat()?.toPx(recyclerview.context).orZero(),
            verticalSpace = verticalSpace.toFloat().toPx(recyclerview.context)
        ).run {
            recyclerview.addItemDecoration(this)
        }
    }
    horizontalSpace?.let {
        RecyclerItemDecoration(
            space = space?.toFloat()?.toPx(recyclerview.context).orZero(),
            horizontalSpace = horizontalSpace.toFloat().toPx(recyclerview.context)
        ).run {
            recyclerview.addItemDecoration(this)
        }
    }
}

@BindingAdapter(value = ["horizontalSpace", "headTailSpace"], requireAll = false)
fun setHorizontalItemDecoration(
    recyclerView: RecyclerView,
    space: Float?,
    headTailSpaceSpace: Float?
) {
    if (recyclerView.itemDecorationCount == 0) {
        recyclerView.addItemDecoration(
            HorizontalItemSpaceDecoration(
                space?.toInt().orZero(),
                headTailSpaceSpace?.toInt().orZero()
            )
        )
    }
}

@BindingAdapter(value = ["verticalSpace", "topBottomSpace"], requireAll = false)
fun setVerticalItemDecoration(
    recyclerView: RecyclerView,
    space: Float?,
    topBottomSpaceSpace: Float?
) {
    recyclerView.addItemDecoration(
        VerticalItemSpaceDecoration(
            space?.toInt().orZero(),
            topBottomSpaceSpace?.toInt().orZero()
        )
    )
}

@BindingAdapter(
    value = ["layout_margin_top", "layout_margin_bottom", "layout_margin_start", "layout_margin_end"],
    requireAll = false
)
fun margin(
    view: ConstraintLayout,
    marginTop: Int?,
    marginBottom: Int?,
    marginStart: Int?,
    marginEnd: Int?
) {
    val params = ConstraintLayout.LayoutParams(view.layoutParams)
    params.setMargins(
        marginStart?.dp ?: 0,
        marginTop?.dp ?: 0,
        marginEnd?.dp ?: 0,
        marginBottom?.dp ?: 0
    )

    view.layoutParams = params
}

@BindingAdapter(value = ["layoutScrolFlagMode"])
fun setLayoutScrollFlagMode(view: CollapsingToolbarLayout, isEmpty: Boolean) {
    val params = view.layoutParams as? AppBarLayout.LayoutParams
    val appBarLayoutParams = view.layoutParams
    if (isEmpty) {
        params?.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
        view.layoutParams = appBarLayoutParams
    } else {
        params?.scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
        view.layoutParams = appBarLayoutParams
    }
}

@BindingAdapter(value = ["throwable"])
fun View.bindThrowable(entity: ThrowEntity?) {
    entity?.run {
        postDelayed({
            context?.showNoticeDialog(message = message, result = {
                if (isFinish.orFalse()) {
                    (context as? Activity)?.finish()
                }
            })
        }, 300)
    }
}

@BindingAdapter(value = ["visibility"])
fun View.setVisibility(value: Boolean?) {
    setVisible(value.orFalse())
}

@BindingAdapter(value = ["inVisibility"])
fun View.setInVisibility(value: Boolean?) {
    setVisible(value.orFalse())
}

@BindingAdapter(value = ["networkVisibility"])
fun View.setNetworkVisibility(value: NetworkStatus?) {
    setVisible(value == NetworkStatus.LOADING)
}

@BindingAdapter(value = ["changeWidth", "changeHeight"])
fun View.setChangeWidthAndHeight(width: Int?, height: Int?) {
    width?.let {
        layoutParams = layoutParams.apply {
            this.width = it
        }
    }
    height?.let {
        layoutParams = layoutParams.apply {
            this.height = it
        }
    }
}

@BindingAdapter(value = ["nestedScrollable"])
fun EditText.setScrollable(scrollable: Boolean?) {
    if (scrollable.orFalse()) {
        setOnTouchListener { view, motionEvent ->
            parent.requestDisallowInterceptTouchEvent(true)
            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    parent.requestDisallowInterceptTouchEvent(false)
                    view.performClick()
                }
            }
            false
        }
    }
}

@BindingAdapter(value = ["enableTransition"], requireAll = false)
fun ViewGroup.setEnableTransition(isEnable: Boolean?) {
    if (isEnable == true) {
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    } else {
        layoutTransition.disableTransitionType(LayoutTransition.CHANGING)
    }
}

@BindingAdapter(value = ["highlightingText", "highlightingTextColor"], requireAll = false)
fun TextView.setHighlightingText(searchText: String?, textColor: Int?) {
    if (!searchText.isNullOrEmpty()) {
        val startIndex = text.indexOf(searchText)
        if (startIndex == -1) {
            return
        }
        text = buildSpannedString {
            append(text)
            setSpan(
                ForegroundColorSpan(textColor ?: R.color.butterscotch),
                startIndex,
                startIndex + searchText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

}