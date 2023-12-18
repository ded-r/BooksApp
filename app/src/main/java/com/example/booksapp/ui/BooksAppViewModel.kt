package com.example.booksapp.ui

import android.net.http.HttpException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.booksapp.BooksApplication
import com.example.booksapp.model.Book
import com.example.booksapp.data.BooksRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BooksAppUiState {
    data class Success(val bookSearch: List<Book>) : BooksAppUiState
    object Loading : BooksAppUiState
    object Error : BooksAppUiState
}

class BooksAppViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    var booksAppUiState: BooksAppUiState by mutableStateOf(BooksAppUiState.Loading)
        private set

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
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
            } catch (e: HttpException) {
                BooksAppUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksAppViewModel(booksRepository = booksRepository)
            }
        }
    }
}

enum class SearchWidgetState {
    OPENED, CLOSED
}