package io.usmon.bootcampnews.domain.repository

import io.usmon.bootcampnews.domain.model.local.entitiy.News
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 5/6/2022.

interface NewsRepository {

    suspend fun insertNews(news: News)

    suspend fun deleteNews(news: News)

    suspend fun getNewsById(newsId: Int?): News?

    fun getNewsByDegree(degree: Int): Flow<List<News>>
}