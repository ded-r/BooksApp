package com.example.booksapp.data

import com.example.booksapp.model.Book
import com.example.booksapp.network.BookService


interface BooksRepository {
    suspend fun getBooks(query: String, maxResults: Int): List<Book>
}

class NetworkBooksRepository(
    private val bookService: BookService
) : BooksRepository {
    override suspend fun getBooks(
        query: String,
        maxResults: Int
    ): List<Book> = bookService.bookSearch(query, maxResults).items.map { items ->
        Book(
            title = items.volumeInfo?.title,
            previewLink = items.volumeInfo?.previewLink,
            imageLink = items.volumeInfo?.imageLinks?.thumbnail
        )
    }
}