package com.febiarifin.inponime.ui.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.febiarifin.inponime.di.Injection
import com.febiarifin.inponime.ui.ViewModelFactory
import com.febiarifin.inponime.ui.common.UiState
import com.febiarifin.inponime.R
import com.febiarifin.inponime.ui.components.FavoriteItem

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteAnimes()
            }
            is UiState.Success -> {
                FavoriteContent(
                    uiState.data,
                    onFavoriteAnimeChanged = { animeId, isFavorite ->
                        viewModel.updateAnimeState(animeId, isFavorite)
                    },
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteContent(
    state: FavoriteState,
    onFavoriteAnimeChanged: (id: Long, isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_favorite),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        if (state.favoriteAnime.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Anime Favorit Kosong")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(state.favoriteAnime, key = { it.anime.id }) { item ->
                    FavoriteItem(
                        animeId = item.anime.id,
                        image = item.anime.image,
                        title = item.anime.title,
                        rating = item.anime.rating,
                        isFavorite = item.isFavorite,
                        onFavoriteAnimeChanged = onFavoriteAnimeChanged,
                        modifier = modifier.clickable {
                            navigateToDetail(item.anime.id)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}