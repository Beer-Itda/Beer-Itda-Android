//package com.ddd4.synesthesia.beer.presentation.ui.main.home.more.item
//
//import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
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