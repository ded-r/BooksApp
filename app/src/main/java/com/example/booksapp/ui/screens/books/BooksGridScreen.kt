package com.example.booksapp.ui.screens.books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.booksapp.R
import com.example.booksapp.model.Book
import kotlin.reflect.KFunction1

@Composable
fun BooksGridScreen(
    books: List<Book>,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    modifier: Modifier,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp), contentPadding = PaddingValues(4.dp)) {
        itemsIndexed(books) { _, book ->
            BookCard(
                book = book,
                onButtonClicked = onButtonClicked,
                setBookAction = setBookAction,
                modifier.padding(1.dp)
            )
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    onButtonClicked: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .requiredHeight(296.dp)
            .clickable {
                setBookAction.invoke(book)
                onButtonClicked.invoke()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            book.title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(top = 4.dp, bottom = 8.dp)
                )
            }
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https")).crossfade(true).build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_image),
                contentDescription = stringResource(
                    id = R.string.content_description
                ),
                contentScale = ContentScale.Crop
            )
        }
    }
}