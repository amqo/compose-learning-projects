package com.amqo.facebookcompose.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amqo.facebookcompose.presentation.home.composable.StatusUpdateBar
import com.amqo.facebookcompose.presentation.home.composable.TabBar
import com.amqo.facebookcompose.presentation.home.composable.TopAppBar
import com.amqo.facebookcompose.ui.theme.FacebookComposeTheme

@Composable
fun HomeScreen(
    onSignInRequired: () -> Unit
) {

    val viewModel = viewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()
    when (state) {
        is HomeScreenState.Loaded -> HomeScreenContent(state as HomeScreenState.Loaded)
        is HomeScreenState.Loading -> LoadingScreen()
        is HomeScreenState.SignInRequired -> LaunchedEffect(Unit) {
            onSignInRequired()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeScreenContent(state: HomeScreenState.Loaded) {
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
                    avatarUrl = state.avatarUrl,
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
        HomeScreen {}
    }
}