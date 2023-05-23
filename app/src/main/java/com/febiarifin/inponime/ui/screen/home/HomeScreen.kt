package com.febiarifin.inponime.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.febiarifin.inponime.di.Injection
import com.febiarifin.inponime.model.FavoriteAnime
import com.febiarifin.inponime.ui.ViewModelFactory
import com.febiarifin.inponime.ui.common.UiState
import com.febiarifin.inponime.ui.components.AnimeItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAnimes()
            }
            is UiState.Success -> {
                HomeContent(
                    favoriteAnime = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    favoriteAnime: List<FavoriteAnime>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(favoriteAnime) { data ->
            AnimeItem(
                image = data.anime.image,
                title = data.anime.title,
                rating = data.anime.rating,
                modifier = Modifier.clickable {
                        navigateToDetail(data.anime.id)
                }
            )
        }
    }
}