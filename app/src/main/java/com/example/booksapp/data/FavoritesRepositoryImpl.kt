package com.example.booksapp.data

import com.example.booksapp.data.local.entities.Favorite
import com.example.bookshelfapp.data.local.FavoritesLocalDataSource
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(private val favoritesLocalDataSource: FavoritesLocalDataSource) :
    FavoritesRepository {
    override fun getAll(): Flow<List<Favorite>> = favoritesLocalDataSource.getAll()
    override suspend fun insertBook(favorite: Favorite) = favoritesLocalDataSource.insertBook(favorite)
    override suspend fun deleteBook(favorite: Favorite) = favoritesLocalDataSource.deleteBook(favorite)
    override fun isFavoriteBook(bookId: String): Flow<Boolean> = favoritesLocalDataSource.isFavoriteBook(bookId)
}