package io.usmon.bootcampnews.data.data_source

import androidx.room.*
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import kotlinx.coroutines.flow.Flow

// Created by Usmon Abdurakhmanv on 5/6/2022.

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)

    @Query("SELECT * FROM news WHERE newsId = :newsId")
    suspend fun getNewsById(newsId: Int?): News?

    @Query("SELECT * FROM news WHERE degree = :degree")
    fun getNewsByDegree(degree: Int): Flow<List<News>>

}