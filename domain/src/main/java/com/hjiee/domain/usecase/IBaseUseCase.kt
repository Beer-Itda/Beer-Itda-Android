package com.hjiee.domain.usecase

import com.hjiee.domain.NetworkCallback

interface IBaseUseCase<RESPONSE> {
    suspend fun execute(
        callback: NetworkCallback<RESPONSE>
    )
}