package com.amqo.facebookcompose.presentation.composables

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

private data class TabItem(
    val icon: ImageVector,
    val contentDescription: String
)

@Composable
fun TabBar() {
    Surface {
        var index by remember {
            mutableStateOf(0)
        }
        TabRow(
            selectedTabIndex = index,
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary
        ) {
            val tabs = listOf(
                TabItem(icon = Icons.Default.Home, contentDescription = "Tab icon ${Icons.Default.Home.name}"),
                TabItem(icon = Icons.Default.Tv, contentDescription = "Tab icon ${Icons.Default.Tv.name}"),
                TabItem(icon = Icons.Default.Store, contentDescription = "Tab icon ${Icons.Default.Store.name}"),
                TabItem(icon = Icons.Default.Newspaper, contentDescription = "Tab icon ${Icons.Default.Newspaper.name}"),
                TabItem(icon = Icons.Default.Notifications, contentDescription = "Tab icon ${Icons.Default.Notifications.name}"),
                TabItem(icon = Icons.Default.Menu, contentDescription = "Tab icon ${Icons.Default.Menu.name}")
            )
            tabs.forEachIndexed { i, tabItem ->
                TabIconButton(
                    selected = index == i,
                    onClick = { index = i },
                    item = tabItem
                )
            }
        }
    }
}

@Composable
private fun TabIconButton(
    selected: Boolean,
    onClick: () -> Unit,
    item: TabItem
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = Modifier.heightIn(48.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.contentDescription,
            tint = if (selected)
                MaterialTheme.colors.primary
            else MaterialTheme.colors.onSurface.copy(
                alpha = .44f
            )
        )
    }
}