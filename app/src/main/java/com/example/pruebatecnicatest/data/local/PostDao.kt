package com.example.pruebatecnicatest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pruebatecnicatest.BuildConfig.DB_NAME
import com.example.pruebatecnicatest.data.local.models.PostData

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PostData)

    @Delete
    suspend fun delete(data: PostData): Int

    @Update
    suspend fun update(data: PostData): Int

    @Query("SELECT * FROM $DB_NAME")
    suspend fun selectAllPost(): List<PostData>

    @Query("SELECT * FROM $DB_NAME WHERE id = :id")
    suspend fun selectById(id:String): PostData
}