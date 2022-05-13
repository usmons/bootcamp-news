package io.usmon.bootcampnews.domain.use_case

import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.repository.NewsRepository

// Created by Usmon Abdurakhmanv on 5/6/2022.

class GetNewsByIdUseCase(
    private val repository: NewsRepository
) {

    suspend operator fun invoke(newsId: Int?): News? {
        return repository.getNewsById(newsId)
    }
}