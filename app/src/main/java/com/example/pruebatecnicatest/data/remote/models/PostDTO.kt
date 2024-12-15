package com.example.pruebatecnicatest.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDTO(
    @SerialName(value = "body") val body: String,
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "title") val title: String,
    @SerialName(value = "userId") val userId: Int
)