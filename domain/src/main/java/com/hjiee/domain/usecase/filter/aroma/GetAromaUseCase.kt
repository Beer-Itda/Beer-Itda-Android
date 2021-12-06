package com.hjiee.domain.usecase.filter.aroma

import com.hjiee.domain.NetworkCallback
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetAromaUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(
        callback: NetworkCallback<List<String>>
    ) {
        coroutineScope {
//            val response: List<String> =
//                repository.getAppConfig().result?.aromaList.orEmpty()
//
//            if (response.isNullOrEmpty()) {
//                callback.invoke(NetworkResult.NoContents())
//            } else {
//                callback.invoke(NetworkResult.Success(response))
//            }
        }
    }
}