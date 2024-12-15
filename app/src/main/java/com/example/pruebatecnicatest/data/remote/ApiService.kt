package com.example.pruebatecnicatest.data.remote

import com.example.pruebatecnicatest.data.remote.models.PostDTO
import retrofit2.http.GET

interface ApiService {
    @GET("post")
    suspend fun fetchTransactions(): List<PostDTO>
}