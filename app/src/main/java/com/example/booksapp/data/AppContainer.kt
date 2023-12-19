package com.example.booksapp.data

import android.content.Context
import com.example.booksapp.network.BookService
import com.example.bookshelfapp.data.local.BooksDatabase
import com.example.bookshelfapp.data.local.FavoritesLocalDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
    val favoritesRepository: FavoritesRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: BookService by lazy {
        retrofit.create(BookService::class.java)
    }

    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitService)
    }

    private val favoritesLocalDataSource by lazy {
        FavoritesLocalDataSource(BooksDatabase.getDatabase(context).favoriteDao())
    }

    override val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepositoryImpl(favoritesLocalDataSource)
    }
}