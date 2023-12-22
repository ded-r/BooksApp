package com.example.booksapp.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.booksapp.R
import com.example.booksapp.model.Book
import com.example.booksapp.data.BookUiState
import com.example.booksapp.ui.BooksAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    book: Book?,
    bookUiState: BookUiState,
    booksAppViewModel: BooksAppViewModel,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null
                )
            }
        })
        book?.title?.let {
            book.imageLink?.let { it1 ->
                ImageContainer(
                    it1, it, modifier = Modifier
                        .padding(top = 15.dp)
                        .weight(1f)
                )
            }
        }
        book?.let {
            BookDetail(
                it,
                { booksAppViewModel.insertBook(bookUiState.currentBook!!) },
                isFavorite = bookUiState.isFavorite,
                modifier = Modifier
                    .padding(15.dp)
                    .weight(2f)
            )
        }
    }
}

@Composable
fun ImageContainer(imageSrc: String, title: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageSrc.replace("http", "https")).crossfade(true).build(),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.Fit,
            contentDescription = title,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BookDetail(
    book: Book, insertBook: () -> Unit, isFavorite: Boolean, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${book.title}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Publisher: ${book.publisher ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(3.dp))
            Text(
                text = "Date published: ${book.publishedDate ?: "Unknown"}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(13.dp))
            Text(
                text = book.description ?: "No Description",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonPreview(
                    context = context, previewLink = book.previewLink.toString()
                )
                IconButton(onClick = insertBook) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

