package com.example.booksapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.booksapp.data.local.entities.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): Flow<List<Favorite>>

    @Insert
    suspend fun insertBook(favorite: Favorite)

    @Delete
    suspend fun deleteBook(favorite: Favorite)

    @Query("SELECT COUNT(*) FROM favorites WHERE id=:bookId")
    fun findBook(bookId: String): Flow<Int>
}