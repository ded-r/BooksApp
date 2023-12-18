package com.example.booksapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.booksapp.model.Book
import com.example.booksapp.ui.BooksAppUiState
import com.example.booksapp.ui.screens.books.BooksGridScreen
import com.example.booksapp.ui.screens.books.ErrorScreen
import com.example.booksapp.ui.screens.books.LoadingScreen
import kotlin.reflect.KFunction1

@Composable
fun HomeScreen(
    booksAppUiState: BooksAppUiState,
    retryAction: () -> Unit,
    setBookAction: KFunction1<Book, Unit>,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    when (booksAppUiState) {
        is BooksAppUiState.Loading -> LoadingScreen(modifier)
        is BooksAppUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
        is BooksAppUiState.Success -> BooksGridScreen(
            books = booksAppUiState.bookSearch,
            onButtonClicked = onButtonClicked,
            setBookAction = setBookAction,
            modifier = modifier,
        )
    }
}