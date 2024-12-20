package com.example.pruebatecnicatest.domain.useCase

import com.example.pruebatecnicatest.data.remote.repository.RemoteRepository
import com.example.pruebatecnicatest.domain.mapper.MainMapper.toDomain
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.utils.WrapperResponse
import javax.inject.Inject

class GetAllPostUseCase(private val remoteRepository: RemoteRepository) {

    suspend fun getAllPost(): WrapperResponse<List<PostDomain>> {
        val resp = try {
            remoteRepository.getTransactions().map {
                it.toDomain()
            }
        } catch (e: Exception) {
            return WrapperResponse.Error(e.message ?: "Se ha producido un error")
        }
        return WrapperResponse.Success(resp)
    }
}