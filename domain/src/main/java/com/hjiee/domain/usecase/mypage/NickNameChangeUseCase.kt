package com.hjiee.domain.usecase.mypage

import com.hjiee.domain.repository.BeerRepository
import javax.inject.Inject

class NickNameChangeUseCase @Inject constructor(
    private val repository: BeerRepository
) {

    suspend fun execute(nickName: String) {
        repository.changeNickName(nickName)
    }
}