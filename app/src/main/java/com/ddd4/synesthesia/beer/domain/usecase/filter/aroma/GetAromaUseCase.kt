package com.ddd4.synesthesia.beer.domain.usecase.filter.aroma

import com.ddd4.synesthesia.beer.data.Result
import com.ddd4.synesthesia.beer.data.ResultCallback
import com.ddd4.synesthesia.beer.data.model.filter.style.StyleLargeCategories
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GetAromaUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(
        callback: ResultCallback<List<String>>
    ) {
        coroutineScope {
            val response: List<String> =
                repository.getAppConfig().result?.aromaList.orEmpty()

            if (response.isNullOrEmpty()) {
                callback.invoke(Result.NoContents())
            } else {
                callback.invoke(Result.Success(response))
            }
        }
    }
}