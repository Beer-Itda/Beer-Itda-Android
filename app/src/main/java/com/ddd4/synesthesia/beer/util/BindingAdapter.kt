package com.ddd4.synesthesia.beer.util

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.ext.toPx
import com.ddd4.synesthesia.beer.util.filter.BeerFilter
import com.ddd4.synesthesia.beer.util.sort.SortType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:addChip")
fun makeChips(chipGroup: ChipGroup, flavor: List<String>) {
    chipGroup.removeAllViews()
    flavor.asSequence().forEach {
        val chip = Chip(chipGroup.context).apply {
            setChipDrawable(ChipDrawable.createFromResource(this.context, R.xml.chip_home_item))
            typeface = ResourcesCompat.getFont(chipGroup.context, R.font.notosans_kr_bold)
            text = it
        }
        chipGroup.addView(chip)
    }
}

@BindingAdapter(value = ["forgroundSelected"])
fun forgroundSelected(view : View, type : InfomationsType) = if(type == InfomationsType.HEADER) {
    view.foreground = null
} else {
    val typeValue = TypedValue()
    view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground,typeValue,true)
    view.foreground = view.context.getDrawable(typeValue.resourceId)
}

@BindingAdapter("app:sortTypeText")
fun sortTypeText(textView: TextView, type: SortType?) {
    type ?: return
    textView.text = when(type) {
        SortType.Default -> textView.resources.getString(R.string.sort_default)
        SortType.Rating -> textView.resources.getString(R.string.sort_rating)
        SortType.Review -> textView.resources.getString(R.string.sort_review)
    }

}

@BindingAdapter(value = ["space","vertical_space","horizontal_space"], requireAll = false)
fun decoration(recyclerview : RecyclerView, space : Int?, verticalSpace : Int?, horizontalSpace : Int?) {
    verticalSpace?.let {
        RecyclerItemDecoration(space = space?.toFloat()?.toPx(recyclerview.context) ?: 0,verticalSpace = verticalSpace.toFloat().toPx(recyclerview.context))
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
    horizontalSpace?.let {
        RecyclerItemDecoration(space = space?.toFloat()?.toPx(recyclerview.context) ?: 0, horizontalSpace = horizontalSpace.toFloat().toPx(recyclerview.context))
            .run {
                recyclerview.addItemDecoration(this)
            }
    }
}

@BindingAdapter("app:updateCountText")
fun updateCountText(countView: TextView, selectedItemList: MutableLiveDataList<String>) {
    if (selectedItemList.isNotEmpty()) {
        val suffix = "${selectedItemList.count().minus(1)}개"
        countView.text =
            if (selectedItemList.count() > 1) "${selectedItemList[0]} 외 $suffix" else selectedItemList[0]
    } else {
        countView.text = ""
    }
}

@BindingAdapter("app:updateAbvRange")
fun updateAbvRange(abvTextView: TextView, abvRange: Pair<Int, Int>?) {
    abvRange ?: return
    val text = "${abvRange.first} - ${abvRange.second}"
    abvTextView.text = text
}

@BindingAdapter("app:filterVisibility")
fun homeFilterVisibility(view: View, filter: BeerFilter?) {
    filter ?: return

    with(filter) {
        view.visibility = if (styleFilter != null || aromaFilter != null || abvFilter != null || countryFilter != null) {
             View.VISIBLE
        } else {
            View.GONE
        }
    }

}
