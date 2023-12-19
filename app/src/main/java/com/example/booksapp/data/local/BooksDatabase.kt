package com.example.bookshelfapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.booksapp.data.local.dao.FavoriteDao
import com.example.booksapp.data.local.entities.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var Instance: BooksDatabase? = null
        fun getDatabase(context: Context): BooksDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BooksDatabase::class.java, "books_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}