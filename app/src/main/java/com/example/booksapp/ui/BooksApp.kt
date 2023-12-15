package com.example.booksapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.booksapp.R
import com.example.booksapp.ui.screens.FavoritesScreen
import com.example.booksapp.ui.screens.HomeScreen
import com.example.booksapp.ui.screens.SearchScreen
import com.example.booksapp.ui.theme.BooksAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksApp(
    modifier: Modifier = Modifier
) {

    val booksAppViewModel: BooksAppViewModel = viewModel(factory = BooksAppViewModel.Factory)
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(modifier = modifier,
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(selected = index == navigationSelectedItem, label = {
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
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            HomeScreen(
//                booksAppUiState = booksAppViewModel.booksAppUiState,
//                retryAction = { booksAppViewModel.getBooks() },
//                modifier = modifier
//            )
//        }
        NavHost(
            navController = navController,
            startDestination = Routes.Browse.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Routes.Browse.route) {
                HomeScreen(
                    booksAppUiState = booksAppViewModel.booksAppUiState,
                    retryAction = { booksAppViewModel.getBooks() },
                    navController = navController,
                    modifier = modifier
                )
            }
            composable(Routes.Search.route) {
                SearchScreen(navController = navController)
            }
            composable(Routes.Favorites.route) {
                FavoritesScreen(navController = navController)
            }
        }
    }
}