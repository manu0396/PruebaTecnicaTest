package com.example.pruebatecnicatest.data.local.repository

import com.example.pruebatecnicatest.data.local.LocalDatabase
import com.example.pruebatecnicatest.data.local.models.PostData
import javax.inject.Inject

class LocalRepository (
    private val localDatabase: LocalDatabase
) {
    suspend fun insertLocalTransaction(data: PostData) {
        return localDatabase.dao().insert(data)
    }

    suspend fun getLocalTransaction(): List<PostData> {
        return localDatabase.dao().selectAllPost()
    }

    suspend fun getLocalTransactionById(id: String): PostData {
        return localDatabase.dao().selectById(id)
    }

    suspend fun deleteLocalPost(data: PostData): Int {
        return localDatabase.dao().delete(data)
    }

    suspend fun updateLocalPost(data: PostData): Int {
        return localDatabase.dao().update(data)
    }
}