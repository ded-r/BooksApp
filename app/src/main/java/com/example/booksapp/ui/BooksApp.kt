package com.example.booksapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booksapp.R
import com.example.booksapp.model.Book
import com.example.booksapp.ui.screens.CategoriesScreen
import com.example.booksapp.ui.screens.FavoritesScreen
import com.example.booksapp.ui.screens.HomeScreen
import com.example.booksapp.ui.screens.books.SearchAppBar
import com.example.booksapp.ui.screens.books.details.DetailsScreen
import com.example.booksapp.ui.utils.BooksAppContentType

enum class BooksAppScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Categories(title = R.string.categories_title),
    Favorites(title = R.string.favorites_title),
    Details(title = R.string.details_title)
}

@Composable
fun BooksApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
) {

    val booksAppViewModel: BooksAppViewModel = viewModel(factory = BooksAppViewModel.Factory)
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()

    val uiState by booksAppViewModel.uiState.collectAsState()


    val searchWidgetState = booksAppViewModel.searchWidgetState
    val searchTextState = booksAppViewModel.searchTextState

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> BooksAppContentType.ListOnly

        WindowWidthSizeClass.Expanded -> BooksAppContentType.ListAndDetail
        else -> BooksAppContentType.ListOnly
    }

    Scaffold(modifier = modifier, topBar = {
        SearchAppBar(
            searchWidgetState = searchWidgetState.value,
            searchTextState = searchTextState.value,
            onTextChange = { booksAppViewModel.updateSearchTextState(newValue = it) },
            onCloseClicked = { booksAppViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
            onSearchClicked = {
                booksAppViewModel.getBooks(it)
            },
            onSearchTriggered = {
                booksAppViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
            }
        )
    }, bottomBar = {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            BottomNavigationItem().bottomNavigationItems()
                .forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = index == navigationSelectedItem,
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        label = {
                            Text(navigationItem.label)
                        }, icon = {
                            Icon(
                                navigationItem.icon, contentDescription = navigationItem.label
                            )
                        }, onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BooksAppScreen.Start.name,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(route = BooksAppScreen.Start.name) {
                HomeScreen(
                    booksAppUiState = booksAppViewModel.booksAppUiState,
                    retryAction = { booksAppViewModel.getBooks() },
                    onButtonClicked = { navController.navigate(BooksAppScreen.Details.name) },
                    setBookAction = booksAppViewModel::setBook,
                    modifier = modifier
                )
            }
            composable(route = BooksAppScreen.Categories.name) {
                CategoriesScreen()
            }
            composable(route = BooksAppScreen.Favorites.name) {
                FavoritesScreen()
            }
            composable(route = BooksAppScreen.Details.name) {
                DetailsScreen(
                    book = uiState.currentBook,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

