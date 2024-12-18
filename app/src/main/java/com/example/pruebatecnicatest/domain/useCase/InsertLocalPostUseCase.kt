package com.example.pruebatecnicatest.domain.useCase

import com.example.pruebatecnicatest.data.local.repository.LocalRepository
import com.example.pruebatecnicatest.domain.mapper.MainMapper.toData
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.utils.WrapperResponse
import javax.inject.Inject

class InsertLocalPostUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend fun insert(data:PostDomain): WrapperResponse<Unit>{
        val resp = try {
            data.isSave = true
            localRepository.insertLocalTransaction(data.toData())
        }catch (e:Exception){
            return WrapperResponse.Error(e.message ?: "Se ha producido un error")
        }
        return WrapperResponse.Success(resp)
    }
}