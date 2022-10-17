package com.amqo.facebookcompose.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amqo.facebookcompose.R
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

@Composable
fun StatusUpdateBar(
    avatarUrl: String,
    onTextChange: (String) -> Unit,
    onSendAction: () -> Unit
) {
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_placeholder)
                    .build(),
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            var text by remember {
                mutableStateOf("")
            }
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(),
                value = text,
                singleLine = true,
                onValueChange = {
                    text = it
                    onTextChange(it)
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.what_is_on_your_mind))
                },
                keyboardActions = KeyboardActions(
                    onSend = { onSendAction() }
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)
            )
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