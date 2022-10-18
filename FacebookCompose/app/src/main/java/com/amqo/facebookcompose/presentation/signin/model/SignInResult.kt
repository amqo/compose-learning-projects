package com.amqo.facebookcompose.presentation.signin.model

sealed class SignInResult {
    data class SignInFailed(
        val error: String
    ): SignInResult()
    object SignInSuccess: SignInResult()
    object SignInCancel: SignInResult()
}
