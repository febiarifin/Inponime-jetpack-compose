package com.febiarifin.inponime.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febiarifin.inponime.data.AnimeRepository
import com.febiarifin.inponime.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: AnimeRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getFavoriteAnimes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getFavoriteAnimes()
                .collect { favoriteAnime ->
                    _uiState.value = UiState.Success(FavoriteState(favoriteAnime))
                }
        }
    }

    fun updateAnimeState(animeId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateAnimeState(animeId, isFavorite)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getFavoriteAnimes()
                    }
                }
        }
    }
}