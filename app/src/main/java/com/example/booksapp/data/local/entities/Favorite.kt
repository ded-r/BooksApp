package com.example.booksapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favorites")
data class Favorite(
    @PrimaryKey val id: String,
    val author: String,
    val title: String,
    @ColumnInfo("image_url") val imageUrl: String
)
