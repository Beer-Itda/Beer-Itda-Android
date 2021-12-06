package com.hjiee.core.event

import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity

interface EventNotifier

interface ActionEventNotifier : EventNotifier {
    fun notifyActionEvent(entity: ActionEntity)
}

/**
 * select event 전송을 위한 인터페이스
 */
interface SelectEventNotifier : EventNotifier {
    fun notifySelectEvent(entity: ItemClickEntity)
}

interface SelectActionEventNotifier : SelectEventNotifier, ActionEventNotifier
