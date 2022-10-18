package com.amqo.facebookcompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amqo.facebookcompose.presentation.home.HomeScreen
import com.amqo.facebookcompose.presentation.signin.SignInScreen
import com.amqo.facebookcompose.ui.theme.FacebookComposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FacebookComposeTheme {
                TransparentSystemBars()
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen {
                            navController.navigate("signin") {
                                popUpTo("home") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable("signin") {
                        SignInScreen {
                            navController.navigate("home") {
                                popUpTo("signin") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TransparentSystemBars() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
    }
}