package io.usmon.bootcampnews.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import io.usmon.bootcampnews.common.Constants.DB_VERSION
import io.usmon.bootcampnews.domain.model.local.entitiy.News

// Created by Usmon Abdurakhmanv on 5/6/2022.

@Database(
    entities = [News::class],
    version = DB_VERSION
)
abstract class NewsDatabase : RoomDatabase() {

    abstract val dao: NewsDao
}