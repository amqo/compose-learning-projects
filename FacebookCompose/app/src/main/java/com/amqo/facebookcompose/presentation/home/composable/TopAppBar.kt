package com.amqo.facebookcompose.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amqo.facebookcompose.R
import com.amqo.facebookcompose.ui.theme.ButtonGray

@Composable
fun TopAppBar() {
    Surface {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(id = R.string.app_name).lowercase(),
                style = MaterialTheme.typography.h6,
            )
            Spacer(Modifier.weight(1f))
            TopBarButton(onClick = {
                // TODO
            }, icon = Icons.Rounded.Search)
            Spacer(Modifier.width(8.dp))
            TopBarButton(onClick = {
                // TODO
            }, icon = Icons.Rounded.ChatBubble)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun TopBarButton(
    onClick: () -> Unit,
    icon: ImageVector
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .background(ButtonGray)
    ) {
        Icon(imageVector = icon, contentDescription = icon.name)
    }
}