package com.ddd4.synesthesia.beer.domain.usecase.filter.aroma

import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import javax.inject.Inject

class GetAromaUseCase @Inject constructor(
    private val repository: BeerRepository
) {

}