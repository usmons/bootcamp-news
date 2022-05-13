package io.usmon.bootcampnews.data.repository

import io.usmon.bootcampnews.data.data_source.NewsDao
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 5/6/2022.

class NewsRepositoryImpl(private val dao: NewsDao) : NewsRepository {

    override suspend fun insertNews(news: News) {
        dao.insertNews(news)
    }

    override suspend fun deleteNews(news: News) {
        dao.deleteNews(news)
    }

    override suspend fun getNewsById(newsId: Int?): News? {
        return dao.getNewsById(newsId)
    }

    override fun getNewsByDegree(degree: Int): Flow<List<News>> {
        return dao.getNewsByDegree(degree)
    }
}