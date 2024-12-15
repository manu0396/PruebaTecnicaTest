package com.example.pruebatecnicatest.data.remote

import com.example.pruebatecnicatest.domain.models.Transaction
import retrofit2.http.GET

interface ApiService {
    @GET("api/transactions")
    suspend fun fetchTransactions(): List<Transaction>
}