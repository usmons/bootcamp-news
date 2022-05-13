package io.usmon.bootcampnews.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.usmon.bootcampnews.common.Constants.DB_NAME
import io.usmon.bootcampnews.data.data_source.NewsDatabase
import io.usmon.bootcampnews.data.repository.NewsRepositoryImpl
import io.usmon.bootcampnews.domain.repository.NewsRepository
import io.usmon.bootcampnews.domain.use_case.*
import javax.inject.Singleton

// Created by Usmon Abdurakhmanv on 5/6/2022.

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): NewsDatabase {
        return Room.databaseBuilder(
            app,
            NewsDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: NewsDatabase): NewsRepository {
        return NewsRepositoryImpl(dao = database.dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: NewsRepository): UseCases {
        return UseCases(
            insertNewsNewsUseCase = InsertNewsUseCase(repository),
            deleteNewsUseCase = DeleteNewsUseCase(repository),
            getNewsUseCase = GetNewsUseCase(repository),
            getNewsByIdUseCase = GetNewsByIdUseCase(repository)
        )
    }
}