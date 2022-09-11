package com.hjiee.presentation.filter.aroma

import com.hjiee.domain.entity.DomainEntity
import com.hjiee.presentation.FakeRepository

class FakeAromaRepository : FakeRepository() {
    override suspend fun getAromaInfo(): List<DomainEntity.Aroma> {
        return listOf(
            DomainEntity.Aroma(
                id = 1,
                name = "",
                isSelected = false
            )

        )
    }
}