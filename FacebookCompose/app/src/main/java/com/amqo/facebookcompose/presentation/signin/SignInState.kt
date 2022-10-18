package com.amqo.facebookcompose.presentation.signin

sealed class SignInState {
    object Pending: SignInState()
    object Completed: SignInState()
}
