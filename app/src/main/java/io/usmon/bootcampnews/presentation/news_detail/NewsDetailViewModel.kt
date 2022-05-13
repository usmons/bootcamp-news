package io.usmon.bootcampnews.presentation.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _news = MutableStateFlow<News?>(null)
    val news = _news.asStateFlow()

    fun followNewsById(newsId: Int?) {
        viewModelScope.launch {
            val news = useCases.getNewsByIdUseCase(newsId)
            _news.value = news
        }
    }

    suspend fun updateNews(copy: News) {
        useCases.insertNewsNewsUseCase(news = copy)
    }

}