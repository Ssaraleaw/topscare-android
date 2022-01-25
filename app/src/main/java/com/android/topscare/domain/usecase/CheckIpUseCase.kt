package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.CheckIpRequest
import com.android.topscare.domain.model.CountRequest
import javax.inject.Inject

class CheckIpUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(checkIpRequest: CheckIpRequest) : Boolean {
        repository.update()
        return repository.checkIp(checkIpRequest)?.resultCode == 200
    }
}