package com.example.booksapp.data

import com.example.booksapp.model.Book

data class BookUiState(
    val bookList: List<Book> = emptyList(),
    val currentBook: Book? = null,
)