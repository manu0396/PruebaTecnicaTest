package com.example.pruebatecnicatest

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pruebatecnicatest.data.local.LocalDatabase
import com.example.pruebatecnicatest.data.local.migrations.Migrations.MIGRATION_1_2
import com.example.pruebatecnicatest.test.BuildConfig.DB_NAME

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pruebatecnicatest", appContext.packageName)
    }

    @Test
    fun migrate1To2() {
        val helper = LocalDatabase::class.java.canonicalName?.let {
            MigrationTestHelper(
                InstrumentationRegistry.getInstrumentation(),
                it,
                FrameworkSQLiteOpenHelperFactory()
            )
        }

        // Create the database with version 1 schema
        helper?.createDatabase("test_db", 1)?.apply {
            // Insert test data if needed
            execSQL("INSERT INTO $DB_NAME (id, body, title, userId) VALUES ('1', 'Test body', 'Test title', '123')")
            close()
        }

        // Run the migration and validate
        helper?.runMigrationsAndValidate("test_db", 2, true, MIGRATION_1_2)?.apply {
            // Query the new schema to ensure the migration is successful
            val cursor = query("SELECT isSave FROM $DB_NAME WHERE id = '1'")
            assertTrue(cursor.moveToFirst())
            val isSaveColumn = cursor.getInt(cursor.getColumnIndexOrThrow("isSave"))
            assertEquals(0, isSaveColumn) // Default value should be 0
            cursor.close()
        }
    }
}