package com.febiarifin.inponime.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febiarifin.inponime.data.AnimeRepository
import com.febiarifin.inponime.model.FavoriteAnime
import com.febiarifin.inponime.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteAnime>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteAnime>>>
        get() = _uiState

    fun getAllAnimes() {
        viewModelScope.launch {
            repository.getAllAnimes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }
}