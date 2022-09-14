package com.amqo.androidcrypto

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amqo.androidcrypto.ui.theme.AndroidCryptoTheme
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cryptoManager = CryptoManager()
        setContent {
            AndroidCryptoTheme {
                var messageToEncrypt by remember {
                    mutableStateOf("")
                }
                var messageToDecrypt by remember {
                    mutableStateOf("")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    TextField(
                        value = messageToEncrypt,
                        onValueChange = {
                            messageToEncrypt = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Encrypt message") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = {
                            val bytes = messageToEncrypt.encodeToByteArray()
                            val file = File(filesDir, "secret.txt")
                            if (!file.exists()) file.createNewFile()
                            val fos = FileOutputStream(file)

                            messageToDecrypt = cryptoManager.encrypt(
                                bytes,
                                fos
                            ).decodeToString()
                        }) {
                            Text("Encrypt")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            val file = File(filesDir, "secret.txt")
                            messageToEncrypt = cryptoManager.decrypt(
                                file.inputStream()
                            ).decodeToString()
                        }) {
                            Text("Decrypt")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(messageToDecrypt)
                }
            }
        }
    }
}

