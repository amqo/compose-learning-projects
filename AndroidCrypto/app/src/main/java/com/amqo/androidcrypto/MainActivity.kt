package com.amqo.androidcrypto

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import com.amqo.androidcrypto.ui.theme.AndroidCryptoTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val Context.dataStore by dataStore(
        fileName = "user-settings.json",
        serializer = UserSettingsSerializer(CryptoManager()),
        corruptionHandler = null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCryptoTheme {

                var username by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var userSettings by remember {
                    mutableStateOf(UserSettings())
                }
                val scope = rememberCoroutineScope()

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                ) {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Username") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Password") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = {
                            scope.launch {
                                dataStore.updateData {
                                    UserSettings(
                                        username = username,
                                        password = password
                                    )
                                }
                            }
                        }) {
                            Text(text = "Save")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            scope.launch {
                                userSettings = dataStore.data.first()
                            }
                        }) {
                            Text(text = "Load")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = userSettings.toString())
                }
            }
        }
    }
}

