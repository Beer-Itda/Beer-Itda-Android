package com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.middle

import com.ddd4.synesthesia.beer.data.model.filter.BeerMiddleType
import com.ddd4.synesthesia.beer.data.model.filter.StyleList
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.filter.style.item.small.StyleSmallItemMapper

object StyleMiddleItemMapper {

    fun getAle(style: StyleList?, eventNotifier: SelectActionEventNotifier) = listOf(
        StyleMiddleItemViewModel(
            name = BeerMiddleType.Ale.name,
            description = "상면 발효 효모를 사용하여\n화려하고 풀부한 향이 나는 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.Ale.name,
                list = style?.ale?.ale.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        ),
        StyleMiddleItemViewModel(
            name = BeerMiddleType.DarkBeer.name,
            description = "페일 에일에 다량의 홉을 넣은,\n홉의 쌉쌀한 향과 맛이 매력적인 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.DarkBeer.name,
                list = style?.ale?.darkBeer.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        ),
        StyleMiddleItemViewModel(
            name = BeerMiddleType.IPA.name,
            description = "페일 에일에 다량의 홉을 넣은,\n홉의 쌉쌀한 향과 맛이 매력적인 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.IPA.name,
                list = style?.ale?.ipa.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        ),
        StyleMiddleItemViewModel(
            name = BeerMiddleType.WheatBeer.name,
            description = "밀 맥아를 높은 비율로 사용한 맥주로\n부드럽고 달콤한 향이 특징인 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.WheatBeer.name,
                list = style?.ale?.wheatBeer.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        )
    )

    fun getLarger(style: StyleList?, eventNotifier: SelectActionEventNotifier) = listOf(
        StyleMiddleItemViewModel(
            name = BeerMiddleType.Lager.name,
            description = "화면 발효 호모를 사용하여\n가벼운 풀비와 시원한 청량감이 매력적인 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.Lager.name,
                list = style?.larger?.larger.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        ),
        StyleMiddleItemViewModel(
            name = BeerMiddleType.Bock.name,
            description = "다양한 원료와 긴 발효기간을 거쳐\n풍부한 맛과 높은 도수를 가진 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.Bock.name,
                list = style?.larger?.bock.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        )
    )

    fun getLambic(style: StyleList?, eventNotifier: SelectActionEventNotifier) = listOf(
        StyleMiddleItemViewModel(
            name = BeerMiddleType.Lambic.name,
            description = "상큼한 맛이 매력적인 자연 발효 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.Lambic.name,
                list = style?.lambic?.lambic.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        )
    )

    fun getEtc(style: StyleList?, eventNotifier: SelectActionEventNotifier) = listOf(
        StyleMiddleItemViewModel(
            name = BeerMiddleType.Etc.name,
            description = "상큼한 맛이 매력적인 자연 발효 맥주",
            list = StyleSmallItemMapper.get(
                title = BeerMiddleType.Etc.name,
                list = style?.etc?.etc.orEmpty(),
                eventNotifier = eventNotifier
            ),
            eventNotifier = eventNotifier
        )
    )
}