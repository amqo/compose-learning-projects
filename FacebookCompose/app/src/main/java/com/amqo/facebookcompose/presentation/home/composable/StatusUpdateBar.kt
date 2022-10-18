package com.amqo.facebookcompose.presentation.home.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amqo.facebookcompose.R

@Composable
fun StatusUpdateBar(
    avatarUrl: String,
    onTextChange: (String) -> Unit,
    onSendAction: () -> Unit
) {
    Surface {
        Column {
            StatusUpdateHeader(avatarUrl, onTextChange, onSendAction)
            Divider()
            StatusActionButtons()
        }
    }
}

@Composable
private fun StatusActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        StatusActionButton(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.VideoCall,
            text = stringResource(R.string.live_status_button),
            onClick = {
                // TODO
            }
        )
        VerticalDivider()
        StatusActionButton(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.PhotoAlbum,
            text = stringResource(R.string.photo_status_button),
            onClick = {
                // TODO
            }
        )
        VerticalDivider()
        StatusActionButton(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.ChatBubble,
            text = stringResource(R.string.discuss_status_button),
            onClick = {
                // TODO
            }
        )
    }
}

@Composable
fun StatusActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.onSurface,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "")
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text)
        }
    }
}

@Composable
private fun StatusUpdateHeader(
    avatarUrl: String,
    onTextChange: (String) -> Unit,
    onSendAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(avatarUrl)
        StatusTextField(onTextChange, onSendAction)
    }
}

@Composable
private fun ProfileImage(avatarUrl: String) {
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
}

@Composable
private fun StatusTextField(
    onTextChange: (String) -> Unit,
    onSendAction: () -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

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
            onSend = {
                if (text.isNotEmpty()) {
                    onSendAction()
                    focusManager.clearFocus(true)
                    text = ""
                }
            }
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send)
    )
}