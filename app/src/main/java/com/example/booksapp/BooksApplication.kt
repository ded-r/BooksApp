package com.example.booksapp

import android.app.Application
import com.example.booksapp.data.AppContainer
import com.example.booksapp.data.DefaultAppContainer

class BooksApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

}