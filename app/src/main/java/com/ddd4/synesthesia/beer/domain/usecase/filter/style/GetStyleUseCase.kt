package com.ddd4.synesthesia.beer.domain.usecase.filter.style

import com.ddd4.synesthesia.beer.data.Result
import com.ddd4.synesthesia.beer.data.ResultCallback
import com.ddd4.synesthesia.beer.data.model.filter.style.StyleLargeCategories
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class GetStyleUseCase @Inject constructor(
    private val repository: BeerRepository
) {
    suspend fun execute(
        callback: ResultCallback<List<StyleLargeCategories>>
    ) {
        coroutineScope {
            val response: List<StyleLargeCategories> =
                repository.getAppConfig().result?.styleLargeCategoriesList.orEmpty()

            if (response.isNullOrEmpty()) {
                callback.invoke(Result.NoContents())
            } else {
                callback.invoke(Result.Success(response))
            }
        }
    }
}