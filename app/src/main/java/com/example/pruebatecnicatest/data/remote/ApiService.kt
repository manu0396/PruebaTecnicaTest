package com.example.pruebatecnicatest.data.remote

import com.example.pruebatecnicatest.BuildConfig.API_URL
import com.example.pruebatecnicatest.data.remote.models.PostDTO
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(private val client: HttpClient) {

    suspend fun fetchTransactions(): List<PostDTO> {
        return client.get {
            url("$API_URL/posts")
            contentType(ContentType.Application.Json)
        }.body()
    }
}