package com.example.pruebatecnicatest.domain.useCase

import com.example.pruebatecnicatest.data.local.repository.LocalRepository
import com.example.pruebatecnicatest.domain.mapper.MainMapper.toData
import com.example.pruebatecnicatest.domain.mapper.MainMapper.toDomain
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.utils.WrapperResponse
import javax.inject.Inject

class GetAllLocalPostUseCase (
    private val localRepository: LocalRepository
) {
    suspend fun getAllLocalPost(): WrapperResponse<List<PostDomain>>{
        val resp = try{
            localRepository.getLocalTransaction().map {
                it.toDomain()
            }
        }catch (e:Exception){
            return WrapperResponse.Error(e.message ?: "Se ha producido un error")
        }
        return WrapperResponse.Success(resp)
    }
}