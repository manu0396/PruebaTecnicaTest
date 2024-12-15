package com.example.pruebatecnicatest.di

import com.example.pruebatecnicatest.data.remote.ApiService
import com.example.pruebatecnicatest.data.remote.repository.PostRepository
import com.example.pruebatecnicatest.domain.useCase.GetAllPostUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                // Set the desired logging level: BASIC, HEADERS, or BODY
                level = HttpLoggingInterceptor.Level.BODY
            }) // Add logging interceptor
            .build())
        .build()
        .create(ApiService::class.java)

    @Provides
    fun providePostRepository(): PostRepository{
        return PostRepository(provideApiService())
    }

    @Provides
    fun provideGetAllPostUseCase(): GetAllPostUseCase{
        return GetAllPostUseCase(providePostRepository())
    }
}