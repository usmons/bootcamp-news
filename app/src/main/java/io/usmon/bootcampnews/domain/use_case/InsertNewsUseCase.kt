package io.usmon.bootcampnews.domain.use_case

import io.usmon.bootcampnews.common.InvalidInputException
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.repository.NewsRepository

// Created by Usmon Abdurakhmanv on 5/6/2022.

class InsertNewsUseCase(
    private val repository: NewsRepository
) {
    @Throws(InvalidInputException::class)
    suspend operator fun invoke(news: News) {
        if (news.title.isBlank())
            throw InvalidInputException("The title couldn't be empty")
        if (news.description.isBlank())
            throw InvalidInputException("The description couldn't be empty")
        repository.insertNews(news)
    }
}