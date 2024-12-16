package com.example.pruebatecnicatest.data.remote.repository

import com.example.pruebatecnicatest.data.local.LocalDatabase
import com.example.pruebatecnicatest.data.local.models.PostData
import com.example.pruebatecnicatest.data.remote.ApiService
import com.example.pruebatecnicatest.data.remote.models.PostDTO
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTransactions(): List<PostDTO> {
        return apiService.fetchTransactions()
    }
}