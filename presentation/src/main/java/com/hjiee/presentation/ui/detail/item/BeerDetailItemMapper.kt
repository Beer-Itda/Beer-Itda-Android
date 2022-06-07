package com.hjiee.presentation.ui.detail.item

import com.hjiee.presentation.ui.common.related.RelatedType
import com.hjiee.presentation.ui.detail.item.info.BeerDetailInfoItemViewModel
import com.hjiee.presentation.ui.detail.item.info.BeerDetailInfoItemViewModelMapper.getInfo
import com.hjiee.presentation.ui.detail.item.related.BeerDetailRelatedListViewModel
import com.hjiee.presentation.ui.detail.item.related.BeerDetailRelatedMapper.getRelatedListItemViewModel
import com.hjiee.presentation.ui.detail.item.review.BeerDetailReviewMapper.getReviewListItemViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object BeerDetailItemMapper {

    fun DomainEntity.BeerDetail?.getDetailViewData(
        eventNotifier: SelectEventNotifier
    ): List<IBeerDetailViewModel> {

        return mutableListOf<IBeerDetailViewModel>().apply {
            this@getDetailViewData?.let {
                add(it.getInfo(eventNotifier = eventNotifier))
                add(it.getReviewListItemViewModel(eventNotifier = eventNotifier))
                add(
                    it.getRelatedListItemViewModel(
                        type = RelatedType.AROMA,
                        eventNotifier = eventNotifier
                    )
                )
                add(
                    it.getRelatedListItemViewModel(
                        type = RelatedType.STYLE,
                        eventNotifier = eventNotifier
                    )
                )
                add(
                    it.getRelatedListItemViewModel(
                        type = RelatedType.RANDOM,
                        eventNotifier = eventNotifier
                    )
                )

            }
        }
    }

    fun List<IBeerDetailViewModel>.findDetailInformation(): BeerDetailInfoItemViewModel {
        return this.filterIsInstance<BeerDetailInfoItemViewModel>().first()
    }

    fun List<IBeerDetailViewModel>.findDetailRelatedBeer(): List<BeerDetailRelatedListViewModel> {
        return this.filterIsInstance<BeerDetailRelatedListViewModel>()
    }

    fun List<IBeerDetailViewModel>.findDetailRelatedStyle(): BeerDetailRelatedListViewModel? {
        return this.filterIsInstance<BeerDetailRelatedListViewModel>().find {
            it.type == RelatedType.STYLE
        }
    }

    fun List<IBeerDetailViewModel>.findDetailRelatedAroma(): BeerDetailRelatedListViewModel? {
        return this.filterIsInstance<BeerDetailRelatedListViewModel>().find {
            it.type == RelatedType.AROMA
        }
    }

    fun List<IBeerDetailViewModel>.findDetailRelatedRandom(): BeerDetailRelatedListViewModel? {
        return this.filterIsInstance<BeerDetailRelatedListViewModel>().find {
            it.type == RelatedType.RANDOM
        }
    }
}