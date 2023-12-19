package com.example.booksapp.data

import com.example.booksapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAll(): Flow<List<Favorite>>
    suspend fun insertBook(favorite: Favorite)
    suspend fun deleteBook(favorite: Favorite)
    fun isFavoriteBook(bookId: String): Flow<Boolean>
}

