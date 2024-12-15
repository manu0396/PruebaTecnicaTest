package com.example.pruebatecnicatest.domain.mapper

import com.example.pruebatecnicatest.data.remote.models.PostDTO
import com.example.pruebatecnicatest.domain.models.PostDomain

object MainMapper {
    fun PostDTO.toDomain(): PostDomain {
        return PostDomain(
            id = id.toString(),
            body = body,
            title = title,
            userId = userId.toString()
        )
    }

    // Mapper from PostDomain to PostDTO
    fun PostDomain.toDTO(): PostDTO {
        return PostDTO(
            id = id.toIntOrNull() ?: 0, // Safely handle invalid integer cases
            body = body,
            title = title,
            userId = userId.toIntOrNull() ?: 0 // Safely handle invalid integer cases
        )
    }
}