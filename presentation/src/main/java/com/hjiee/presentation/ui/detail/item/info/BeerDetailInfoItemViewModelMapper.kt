package com.hjiee.presentation.ui.detail.item.info

import com.hjiee.presentation.ui.common.beer.item.BeerItemViewModelMapper.getBeerItemViewModel
import com.hjiee.core.event.SelectEventNotifier
import com.hjiee.domain.entity.DomainEntity

object BeerDetailInfoItemViewModelMapper {

    fun DomainEntity.BeerDetail.getInfo(
        eventNotifier: SelectEventNotifier
    ): BeerDetailInfoItemViewModel {
        return BeerDetailInfoItemViewModel(
            beer = beer.getBeerItemViewModel(eventNotifier = eventNotifier)
        )
    }
}