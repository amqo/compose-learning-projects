package com.amqo.facebookcompose.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amqo.facebookcompose.ui.theme.FacebookComposeTheme

@Composable
fun HomeScreen() {
    Box(
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                TopAppBar()
            }
            item {
                TabBar()
            }
            item {
                StatusUpdateBar(
                    avatarUrl = "https://static.jobscan.co/blog/uploads/hickman-testimonial-1.png",
                    onTextChange = {
                        // TODO
                    },
                    onSendAction = {
                        // TODO
                    }
                )
            }
        }
    }
}



@Preview(widthDp = 400, heightDp = 300)
@Composable
fun HomeScreenPreview() {
    FacebookComposeTheme {
        HomeScreen()
    }
}