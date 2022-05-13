package io.usmon.bootcampnews.presentation.news_pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.usmon.bootcampnews.common.InvalidInputException
import io.usmon.bootcampnews.domain.model.local.entitiy.News
import io.usmon.bootcampnews.domain.use_case.UseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsPagerViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: UiEvent) {
        viewModelScope.launch {
            when (event) {
                is UiEvent.ShowAddDialog -> {
                    _uiEvent.emit(UiEvent.ShowAddDialog)
                }
                is UiEvent.CloseAddDialog -> {
                    _uiEvent.emit(UiEvent.CloseAddDialog)
                }
                is UiEvent.SaveNews -> {
                    try {
                        useCases.insertNewsNewsUseCase(event.news)
                        _uiEvent.emit(UiEvent.CloseAddDialog)
                        _uiEvent.emit(UiEvent.ShowToast("Successfully saved!"))
                    } catch (e: InvalidInputException) {
                        _uiEvent.emit(UiEvent.ShowToast(e.message))
                    }
                }
                else -> Unit
            }
        }
    }


    sealed class UiEvent {
        object ShowAddDialog : UiEvent()
        object CloseAddDialog : UiEvent()
        data class SaveNews(val news: News) : UiEvent()
        data class ShowToast(val message: String) : UiEvent()
    }
}