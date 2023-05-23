package com.febiarifin.inponime.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.febiarifin.inponime.data.AnimeRepository
import com.febiarifin.inponime.model.Anime
import com.febiarifin.inponime.model.FavoriteAnime
import com.febiarifin.inponime.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailAnimeViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteAnime>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteAnime>>
        get() = _uiState

    fun getAnimeById(animeId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAnimeById(animeId))
        }
    }

    fun addToFavorite(anime: Anime, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.updateAnimeState(anime.id, isFavorite)
        }
    }
}