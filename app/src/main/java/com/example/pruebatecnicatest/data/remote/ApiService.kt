package com.example.pruebatecnicatest.data.remote

import com.example.pruebatecnicatest.data.remote.models.PostDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("posts")
    suspend fun fetchTransactions(): List<PostDTO>
}