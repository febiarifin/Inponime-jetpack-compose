package com.febiarifin.inponime.data

import com.febiarifin.inponime.model.FakeAnimeDataSource
import com.febiarifin.inponime.model.FavoriteAnime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AnimeRepository {
    private val favoriteAnimes = mutableListOf<FavoriteAnime>()

    init {
        if (favoriteAnimes.isEmpty()) {
            FakeAnimeDataSource.dummyAnimes.forEach {
                favoriteAnimes.add(FavoriteAnime(it, false))
            }
        }
    }

    fun getAllAnimes(): Flow<List<FavoriteAnime>> {
        return flowOf(favoriteAnimes)
    }

    fun getAnimeById(animeId: Long): FavoriteAnime {
        return favoriteAnimes.first {
            it.anime.id == animeId
        }
    }

    fun updateAnimeState(animeId: Long, state: Boolean): Flow<Boolean> {
        val index = favoriteAnimes.indexOfFirst { it.anime.id == animeId }
        val result = if (index >= 0) {
            val favoriteAnime = favoriteAnimes[index]
            favoriteAnimes[index] =
                favoriteAnime.copy(anime = favoriteAnime.anime, isFavorite = state)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoriteAnimes(): Flow<List<FavoriteAnime>> {
        return getAllAnimes()
            .map { favoriteAnimes ->
                favoriteAnimes.filter { favoriteAnime ->
                    favoriteAnime.isFavorite != false
                }
            }
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}