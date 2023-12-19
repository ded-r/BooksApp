package com.example.booksapp.model

import com.example.booksapp.data.local.entities.Favorite

data class Book(
    val id: String?,
    val title: String?,
    val publisher: String?,
    val publishedDate: String?,
    val description: String?,
    val previewLink: String?,
    val imageLink: String?
) {
    fun convertBookInfoToFavoriteEntity(): Favorite = Favorite(
        id = id.toString(),
        author = publisher.toString(),
        title = title.toString(),
        imageUrl = imageLink.toString()
    )
}