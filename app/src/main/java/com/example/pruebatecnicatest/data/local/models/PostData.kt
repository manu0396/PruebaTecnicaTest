package com.example.pruebatecnicatest.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebatecnicatest.BuildConfig.DB_NAME

@Entity(tableName = DB_NAME)
data class PostData (
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "userId") val userId: String
)