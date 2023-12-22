package com.example.booksapp.ui.screens.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.booksapp.R

@Composable
fun ButtonPreview(
    context: Context,
    previewLink: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(previewLink))
            context.startActivity(urlIntent)
        }
    ) {
        Text(text = stringResource(id = R.string.preview))
    }
}