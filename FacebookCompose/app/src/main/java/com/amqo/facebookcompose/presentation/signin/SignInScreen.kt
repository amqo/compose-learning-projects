package com.amqo.facebookcompose.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Facebook
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignInScreen(
    onSigInCompleted: () -> Unit
) {
    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsState()

    when(state) {
        SignInState.Completed -> onSigInCompleted()
        SignInState.Pending -> SignInContent()
    }
}

@Composable
private fun SignInContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Rounded.Facebook,
                contentDescription = "${Icons.Rounded.Facebook.name} icon",
                modifier = Modifier.size(90.dp),
                tint = MaterialTheme.colors.primary
            )
            Spacer(Modifier.height(20.dp))
            Text("Sign In with Facebook")
        }
    }
}