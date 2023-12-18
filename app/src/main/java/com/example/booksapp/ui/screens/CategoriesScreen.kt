package com.example.booksapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale.Category

@Composable
fun CategoriesScreen() {
    Column {
        Text(text = "Categories")
    }
}

//@Composable
//fun CategoryCard(
//    setCategoryAction: () -> Unit,
//    getBooksAction: () -> Unit,
//    setScreenAction:()->Unit,
//    @DrawableRes imageRes:Int,
//    category: String,
//    modifier: Modifier = Modifier
//){
//    Card(
//        shape = RoundedCornerShape(0.dp),
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable {
//                setCategoryAction.invoke()
//                getBooksAction.invoke()
//                setScreenAction.invoke()
//            }
//    ){
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ){
//            Image(
//                painter = painterResource(imageRes),
//                contentDescription = stringResource(R.string.loading),
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(5.dp)
//            )
//            Text(
//                text = category,
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 10.dp)
//            )
//        }
//
//    }
//}
