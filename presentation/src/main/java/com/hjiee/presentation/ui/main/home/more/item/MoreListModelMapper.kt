//package com.hjiee.presentation.ui.main.home.more.item
//
//import com.hjiee.presentation.base.event.SelectActionEventNotifier
//
//object MoreListModelMapper {
//
//    fun Response?.getMapper(
//        eventNotifier: SelectActionEventNotifier
//    ): List<MoreListItemViewModel> {
//        return this?.beers?.map {
//            MoreListItemViewModel(
//                beer = it.getBeerItemViewModel(
//                    eventNotifier = eventNotifier
//                ),
//                eventNotifier = eventNotifier
//            )
//        } ?: kotlin.run { emptyList() }
//    }
//
//}