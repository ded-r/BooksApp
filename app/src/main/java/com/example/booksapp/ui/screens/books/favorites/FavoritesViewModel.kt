package com.example.bookshelfapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.booksapp.BooksApplication
import com.example.booksapp.data.FavoritesRepository
import com.example.booksapp.data.local.entities.Favorite
import com.example.booksapp.ui.screens.books.favorites.FavoritesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _favoriteUiState = MutableStateFlow(FavoritesUiState())
    val favoritesUiState = _favoriteUiState.asStateFlow()

    init {
        getAllFavorites()
    }

    fun setFavorite(favorite: Favorite) {
        _favoriteUiState.update { currentState ->
            currentState.copy(
                currentFavorite = favorite,
            )
        }
    }

    fun deleteBook(favorite: Favorite) = viewModelScope.launch {
        favoritesRepository.deleteBook(favorite)
    }

    private fun getAllFavorites() = viewModelScope.launch {
        favoritesRepository.getAll().collect { listOfFavorites ->
            _favoriteUiState.value = FavoritesUiState(listOfFavorites)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooksApplication)
                val favoritesRepository = application.container.favoritesRepository
                FavoritesViewModel(favoritesRepository = favoritesRepository)
            }
        }
    }
}