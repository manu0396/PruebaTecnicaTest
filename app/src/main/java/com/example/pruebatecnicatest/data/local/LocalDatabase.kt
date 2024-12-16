package com.example.pruebatecnicatest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnicatest.data.local.models.PostData

@Database(
    entities = [
        PostData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun dao(): PostDao
}