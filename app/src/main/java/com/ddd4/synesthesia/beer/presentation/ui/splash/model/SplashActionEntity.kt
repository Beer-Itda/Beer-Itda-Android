package com.ddd4.synesthesia.beer.presentation.ui.splash.model

import com.hjiee.core.event.entity.ActionEntity

sealed class SplashActionEntity : ActionEntity(){
    class ForceUpdate(val message: String) : SplashActionEntity()
    class RecommendUpdate(val message: String) : SplashActionEntity()
}