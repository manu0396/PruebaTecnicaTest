package com.example.pruebatecnicatest.di

import android.content.Context
import androidx.room.Room
import com.example.pruebatecnicatest.BuildConfig
import com.example.pruebatecnicatest.data.local.LocalDatabase
import com.example.pruebatecnicatest.data.local.PostDao
import com.example.pruebatecnicatest.data.local.repository.LocalRepository
import com.example.pruebatecnicatest.domain.useCase.DeleteLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetAllLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.GetLocalPostByIdUseCase
import com.example.pruebatecnicatest.domain.useCase.InsertLocalPostUseCase
import com.example.pruebatecnicatest.domain.useCase.UpdateLocalPostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context):LocalDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            BuildConfig.DB_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun providesDao(localDatabase: LocalDatabase):PostDao{
        return localDatabase.dao()
    }

    @Provides
    @Singleton
    fun providesLocalRepository(localDatabase: LocalDatabase):LocalRepository{
        return LocalRepository(localDatabase)
    }

    @Provides
    @Singleton
    fun providesInsertLocalPostUseCase(localRepository: LocalRepository): InsertLocalPostUseCase{
        return InsertLocalPostUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun providesGetAllLocalPostUseCase(localRepository: LocalRepository):GetAllLocalPostUseCase{
        return GetAllLocalPostUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun providesGetPostByIdUseCase(localRepository: LocalRepository):GetLocalPostByIdUseCase{
        return GetLocalPostByIdUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteLocalPostUseCase(localRepository: LocalRepository): DeleteLocalPostUseCase{
        return DeleteLocalPostUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateLocalPostUseCase(localRepository: LocalRepository): UpdateLocalPostUseCase{
        return UpdateLocalPostUseCase(localRepository)
    }
}