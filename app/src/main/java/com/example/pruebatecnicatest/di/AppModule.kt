package com.example.pruebatecnicatest.di

import com.example.pruebatecnicatest.data.remote.ApiService
import com.example.pruebatecnicatest.data.remote.repository.RemoteRepository
import com.example.pruebatecnicatest.domain.useCase.DeleteLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetLocalPostByIdUseCase
import com.example.pruebatecnicatest.domain.useCase.InsertLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.UpdateLocalPostUseCase
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel

import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging){
                level = LogLevel.BODY // Adjust log level as needed (ALL, HEADERS, BODY, INFO, etc.)
                logger = Logger.DEFAULT
            }
        }
    }

    single { FirebaseAuth.getInstance() }

    single { ApiService(get())}

    single { RemoteRepository(get()) }

    single { GetAllPostUseCase(get()) }
    single { InsertLocalPostUseCase(get()) }
    single { DeleteLocalPostUseCase(get()) }
    single { UpdateLocalPostUseCase(get()) }
    single { GetLocalPostByIdUseCase(get()) }
    single { GetAllLocalPostUseCase(get()) }

    viewModel {
        TransactionViewModel(
            getAllPostUseCase = get(),
            insertLocalPostUseCase = get(),
            deleteLocalPostUseCase = get(),
            updateLocalPostUseCase = get(),
            getLocalPostByIdUseCase = get(),
            getAllLocalPostUseCase = get()
        )
    }
}