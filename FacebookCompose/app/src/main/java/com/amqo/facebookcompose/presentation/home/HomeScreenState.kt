package com.amqo.facebookcompose.presentation.home

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object SignInRequired : HomeScreenState()

    data class Loaded(
        val avatarUrl: String
    ) : HomeScreenState()
}
