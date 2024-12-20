package com.example.pruebatecnicatest.di

import androidx.room.Room
import com.example.pruebatecnicatest.BuildConfig
import com.example.pruebatecnicatest.data.local.LocalDatabase
import com.example.pruebatecnicatest.data.local.migrations.Migrations
import com.example.pruebatecnicatest.data.local.repository.LocalRepository
import com.example.pruebatecnicatest.domain.useCase.DeleteLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetLocalPostByIdUseCase
import com.example.pruebatecnicatest.domain.useCase.InsertLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.UpdateLocalPostUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val LocalModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            LocalDatabase::class.java,
            BuildConfig.DB_NAME
        ).addMigrations(Migrations.MIGRATION_1_2)
            .build()
    }

    single {
        val localDatabase: LocalDatabase = get()
        localDatabase.dao()
    }

    single {
        val localDatabase:LocalDatabase = get()
        LocalRepository(localDatabase)
    }

    // Provide Use Cases
    single {
        val localRepository: LocalRepository = get()
        InsertLocalPostUseCase(localRepository)
    }

    single {
        val localRepository: LocalRepository = get()
        GetAllLocalPostUseCase(localRepository)
    }

    single {
        val localRepository: LocalRepository = get()
        GetLocalPostByIdUseCase(localRepository)
    }

    single {
        val localRepository: LocalRepository = get()
        DeleteLocalPostUseCase(localRepository)
    }

    single {
        val localRepository: LocalRepository = get()
        UpdateLocalPostUseCase(localRepository)
    }
}