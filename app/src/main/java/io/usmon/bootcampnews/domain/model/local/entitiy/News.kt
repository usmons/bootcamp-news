package io.usmon.bootcampnews.domain.model.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

// Created by Usmon Abdurakhmanv on 5/6/2022.

@Entity
data class News(
    val title: String,
    val description: String,
    val degree: Int,
    @PrimaryKey val newsId: Int? = null
)