package com.example.booksapp.ui.screens.favorites

import com.example.booksapp.data.local.entities.Favorite

data class FavoritesUiState(
    val listOfFavorites: List<Favorite> = emptyList(),
    val currentFavorite: Favorite? = null
)
