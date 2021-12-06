package com.hjiee.domain.usecase.filter.style

import com.hjiee.domain.NetworkCallback
import com.hjiee.domain.entity.DomainEntity
import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetStyleUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(
        callback: NetworkCallback<List<DomainEntity.StyleLargeCategory>>
    ) {
        coroutineScope {
//            val response: List<DomainEntity.StyleLargeCategory> =
//                repository.getAppConfig().result?.styleLargeCategoriesList.orEmpty()
//
//            if (response.isNullOrEmpty()) {
//                callback.invoke(NetworkResult.NoContents())
//            } else {
//                callback.invoke(NetworkResult.Success(response))
//            }
        }
    }
}