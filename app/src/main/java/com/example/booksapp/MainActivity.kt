package com.example.booksapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import com.example.booksapp.ui.theme.BooksAppTheme
import com.example.booksapp.ui.BooksApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BooksAppTheme {
                BooksApp(onBookClicked = {
                    ContextCompat.startActivity(
                        this,
                        Intent(Intent.ACTION_VIEW, Uri.parse(it.previewLink)),
                        null
                    )
                })
            }
        }
    }
}