package com.example.booksapp.ui

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.booksapp.BooksApplication
import com.example.booksapp.data.BookUiState
import com.example.booksapp.model.Book
import com.example.booksapp.data.BooksRepository
import com.example.booksapp.data.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BooksAppUiState {
    data class Success(val bookSearch: List<Book>) : BooksAppUiState
    object Loading : BooksAppUiState
    object Error : BooksAppUiState
}

class BooksAppViewModel(
    private val booksRepository: BooksRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    var booksAppUiState: BooksAppUiState by mutableStateOf(BooksAppUiState.Loading)
        private set

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun setBook(book: Book) {
        _uiState.update { currentState ->
            currentState.copy(
                currentBook = book,
            )
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    //    fun isFavorite() = viewModelScope.launch {
//        favoritesRepository.isFavoriteBook(bookId = _uiState.value.currentBook?.id.toString()).collect{
//            _detailUiState.value = (_detailUiState.value as DetailUiState.Success).copy(isFavorite = it)
//        }
//    }
    fun isFavorite(book: Book) = viewModelScope.launch {
        favoritesRepository.isFavoriteBook(bookId = book.id.toString()).collect { isFavorite ->
            _uiState.value = _uiState.value.copy(isFavorite = isFavorite)
        }
    }

    fun insertBook(book: Book) {
        viewModelScope.launch {
            try {
                favoritesRepository.insertBook(book.convertBookInfoToFavoriteEntity())
            } catch (e: SQLiteConstraintException) {
                favoritesRepository.deleteBook(book.convertBookInfoToFavoriteEntity())
            }
        }
    }


    init {
        getBooks()
    }


    fun getBooks(query: String = "books", maxResults: Int = 40) {
        viewModelScope.launch {
            booksAppUiState = BooksAppUiState.Loading
            booksAppUiState = try {
                BooksAppUiState.Success(booksRepository.getBooks(query, maxResults))
            } catch (e: IOException) {
                BooksAppUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                val favoritesRepository = application.container.favoritesRepository
                BooksAppViewModel(
                    booksRepository = booksRepository,
                    favoritesRepository = favoritesRepository
                )
            }
        }
    }
}

enum class SearchWidgetState {
    OPENED, CLOSED
}