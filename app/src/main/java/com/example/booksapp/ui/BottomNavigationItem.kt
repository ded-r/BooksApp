package com.example.booksapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    fun bottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Browse",
                icon = Icons.Filled.List,
                route = BooksAppScreen.Start.name
            ),
            BottomNavigationItem(
                label = "Search",
                icon = Icons.Filled.Search,
                route = BooksAppScreen.Search.name
            ),
            BottomNavigationItem(
                label = "Favorites",
                icon = Icons.Filled.Favorite,
                route = BooksAppScreen.Favorites.name
            ),
        )
    }
}