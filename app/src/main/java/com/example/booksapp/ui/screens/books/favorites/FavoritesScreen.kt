package com.example.booksapp.ui.screens.books.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.booksapp.R
import com.example.booksapp.data.local.entities.Favorite
import com.example.bookshelfapp.ui.screens.favorites.FavoritesViewModel
import kotlin.reflect.KFunction1

@Composable
fun FavoritesScreen(
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Favorite, Unit>,
    favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
) {
    val favoritesUiState by favoritesViewModel.favoritesUiState.collectAsState()
    if (favoritesUiState.listOfFavorites.isEmpty()) {
        FavoritesEmptyScreen(modifier = Modifier.fillMaxSize())
    } else {
        FavoritesListScreen(
            listOfFavorites = favoritesUiState.listOfFavorites,
            onButtonClicked = onButtonClicked,
            showButtonDelete = true,
            setBookAction = setBookAction,
            favoritesViewModel = favoritesViewModel,
            modifier = Modifier
        )
    }
}

@Composable
fun FavoritesListScreen(
    modifier: Modifier = Modifier,
    listOfFavorites: List<Favorite>,
    favoritesViewModel: FavoritesViewModel,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Favorite, Unit>,
    showButtonDelete: Boolean = false
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp), contentPadding = PaddingValues(4.dp)) {
        items(
            count = listOfFavorites.size,
            key = {
                listOfFavorites[it].id
            },
            itemContent = { index ->
                val favoriteData = listOfFavorites[index]
                Card(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .requiredHeight(296.dp)
                        .clickable {
                            setBookAction.invoke(listOfFavorites[index])
                            onButtonClicked.invoke()
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = favoriteData.title,
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(top = 4.dp, bottom = 8.dp)
                        )
                    }
                    AsyncImage(
                        modifier = modifier.fillMaxWidth(),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(favoriteData.imageUrl.replace("http", "https"))
                            .crossfade(true)
                            .build(),
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loading_image),
                        contentDescription = stringResource(
                            id = R.string.content_description
                        ),
                        contentScale = ContentScale.Crop
                    )
                    if (showButtonDelete) {
                        IconButton(onClick = { favoritesViewModel.deleteBook(favoriteData) }) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = ""
                            )
                        }
                    }
                }
            })
    }
}

@Composable
fun FavoritesEmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val text = stringResource(id = R.string.favorites_is_empty)
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}
