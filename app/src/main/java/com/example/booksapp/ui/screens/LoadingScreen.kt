package com.example.booksapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.booksapp.R

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.loading_image),
            contentDescription = stringResource(
                id = R.string.loading
            )
        )
    }
}