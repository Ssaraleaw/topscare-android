package com.android.topscare.domain.usecase

import com.android.topscare.domain.data.repository.TopsCareRepository
import com.android.topscare.domain.model.ReceiveRequest
import javax.inject.Inject

class InsertReceiveProductUseCase @Inject constructor(
    private val repository: TopsCareRepository
){
    suspend operator fun invoke(receiveRequest: ReceiveRequest) : Boolean {
        return repository.insertReceiveProduct(receiveRequest)?.resultCode == 200
    }
}