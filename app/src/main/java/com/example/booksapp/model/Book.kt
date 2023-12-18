package com.example.booksapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.FileDescriptor
@Parcelize
data class Book(
    val id: String?,
    val title: String?,
    val publisher: String?,
    val description: String?,
    val previewLink: String?,
    val imageLink: String?
) : Parcelable