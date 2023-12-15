package com.example.booksapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.booksapp.ui.theme.BooksAppUiState

@Composable
fun HomeScreen(
    booksAppUiState: BooksAppUiState,
    retryAction: () -> Unit,
    navController: NavController,
    modifier: Modifier
) {
    when (booksAppUiState) {
        is BooksAppUiState.Loading -> LoadingScreen(modifier)
        is BooksAppUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
        is BooksAppUiState.Success -> BooksGridScreen(
            books = booksAppUiState.bookSearch,
            modifier = modifier
        )
    }
}