package io.usmon.bootcampnews.domain.use_case

// Created by Usmon Abdurakhmanv on 5/6/2022.

data class UseCases(
    val insertNewsNewsUseCase: InsertNewsUseCase,
    val deleteNewsUseCase: DeleteNewsUseCase,
    val getNewsByIdUseCase: GetNewsByIdUseCase,
    val getNewsUseCase: GetNewsUseCase,
)
