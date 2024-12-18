package com.example.pruebatecnicatest.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pruebatecnicatest.BuildConfig.DB_NAME

object Migrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Add the new 'isSave' column to the 'PostData' table with a default value of 0
            db.execSQL("ALTER TABLE $DB_NAME ADD COLUMN isSave INTEGER NOT NULL DEFAULT 0")
        }
    }
}