package com.hjiee.domain.usecase.filter.style

import com.hjiee.domain.repository.BeerRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetStyleUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute() {
        repository.getStyleInfo()
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