package io.usmon.bootcampnews.domain.use_case

import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 5/6/2022.

class GetNewsUseCase(
    private val repository: NewsRepository
) {

    operator fun invoke(degree: Int): Flow<List<News>> {
        return repository.getNewsByDegree(degree)
    }

}