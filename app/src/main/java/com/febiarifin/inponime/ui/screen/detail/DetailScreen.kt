package com.febiarifin.inponime.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.febiarifin.inponime.R
import com.febiarifin.inponime.di.Injection
import com.febiarifin.inponime.ui.ViewModelFactory
import com.febiarifin.inponime.ui.common.UiState
import com.febiarifin.inponime.ui.components.FavoriteButton
import com.febiarifin.inponime.ui.theme.InponimeTheme

@Composable
fun DetailScreen(
    animeId: Long,
    viewModel: DetailAnimeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToFavorite: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAnimeById(animeId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.anime.image,
                    data.anime.title,
                    data.anime.description,
                    data.anime.genre,
                    data.anime.rating,
                    onBackClick = navigateBack,
                    onAddToFavorite = { count ->
                        viewModel.addToFavorite(data.anime, true)
                        navigateToFavorite()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    description: String,
    genre: String,
    rating: String,
    onBackClick: () -> Unit,
    onAddToFavorite: (isFavorite: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isAnimeFavorite by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp)
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(4.dp))
                Text(
                    text = genre    ,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = modifier.height(4.dp))
                Box(
                    modifier = modifier
                        .clip(shape = RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colors.secondary)
                        .padding(4.dp, 1.dp, 4.dp, 1.dp)
                ) {
                    Row(
                        modifier = modifier.padding(),
                    ) {
                        Text(
                            text = rating,
                            fontSize = 12.sp,
                            color = Color.White,
                        )
                        Spacer(modifier = modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = modifier
                                .size(13.dp)
                        )
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            FavoriteButton(
                isFavorite = isAnimeFavorite,
                onClick = {
                    if (isAnimeFavorite) {
                        onAddToFavorite(false)
                    } else {
                        onAddToFavorite(true)
                    }
                },
                modifier = modifier.fillMaxWidth().height(52.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    InponimeTheme {
        DetailContent(
            R.drawable.naruto,
            "Naruto Shippuden",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "Action, Fantasi, Komedi",
            "8.90",
            onBackClick = {},
            onAddToFavorite = {}
        )
    }
}