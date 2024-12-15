package com.example.pruebatecnicatest.data.remote.repository

import com.example.pruebatecnicatest.data.remote.ApiService
import com.example.pruebatecnicatest.domain.models.Transaction
import javax.inject.Inject

class TransactionRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getTransactions(): List<Transaction> {
        return apiService.fetchTransactions()
    }
}