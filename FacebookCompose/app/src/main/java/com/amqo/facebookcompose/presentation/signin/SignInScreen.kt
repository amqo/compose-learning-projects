package com.amqo.facebookcompose.presentation.signin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Facebook
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.amqo.facebookcompose.R
import com.amqo.facebookcompose.presentation.signin.model.SignInResult
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit
) {
    val context = LocalContext.current
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
            SignInButton(onSigInResult = {
                when(it) {
                    SignInResult.SignInCancel ->
                        Toast.makeText(context, context.getString(R.string.sign_in_cancel), Toast.LENGTH_SHORT).show()
                    is SignInResult.SignInFailed ->
                        Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    is SignInResult.SignInSuccess -> {
                        Toast.makeText(context, context.getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show()
                        onSignInSuccess()
                    }
                }
            })
        }
    }
}


@Composable
fun SignInButton(
    onSigInResult: (SignInResult) -> Unit
) {
    val scope = rememberCoroutineScope()
    AndroidView ({ context ->
        LoginButton(context).apply {
            val callbackManager = CallbackManager.Factory.create()
            setPermissions("email", "public_profile")
            registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
                override fun onCancel() {
                    onSigInResult(SignInResult.SignInCancel)
                }
                override fun onError(error: FacebookException) {
                    onSigInResult(SignInResult.SignInFailed("Fb login error: ${error.localizedMessage}"))
                }
                override fun onSuccess(result: LoginResult) {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    scope.launch {
                        Firebase.auth.signInWithCredential(credential).await().apply {
                            user?.let {
                                onSigInResult(SignInResult.SignInSuccess)
                            } ?: kotlin.run {
                                onSigInResult(SignInResult.SignInFailed("Could not get user from Fb login"))
                            }
                        }
                    }
                }
            })
        }
    })
}