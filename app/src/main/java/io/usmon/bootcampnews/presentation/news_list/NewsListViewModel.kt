package io.usmon.bootcampnews.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _news = MutableStateFlow<List<News>>(emptyList())
    val news: StateFlow<List<News>> = _news.asStateFlow()

    fun followNewsByDegree(degree: Int) {
        viewModelScope.launch {
            useCases.getNewsUseCase(degree).collectLatest { news ->
                _news.value = news
            }
        }
    }

    fun deleteNews(news: News) {
        viewModelScope.launch {
            useCases.deleteNewsUseCase(news)
        }
    }

    suspend fun updateNews(copy: News) {
        useCases.insertNewsNewsUseCase(news = copy)
    }
}