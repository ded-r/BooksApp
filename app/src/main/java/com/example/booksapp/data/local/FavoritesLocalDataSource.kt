package com.example.bookshelfapp.data.local

import com.example.booksapp.data.local.dao.FavoriteDao
import com.example.booksapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesLocalDataSource(private val favoriteDao: FavoriteDao) {
    fun getAll(): Flow<List<Favorite>> = favoriteDao.getAll()
    suspend fun insertBook(favorite: Favorite) = favoriteDao.insertBook(favorite)
    suspend fun deleteBook(favorite: Favorite) = favoriteDao.deleteBook(favorite)
    fun isFavoriteBook(bookId: String): Flow<Boolean> = flow {
        favoriteDao.findBook(bookId).collect{
            emit(it>0)
        }
    }
}