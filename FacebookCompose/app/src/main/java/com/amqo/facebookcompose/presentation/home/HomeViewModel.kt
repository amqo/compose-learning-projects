package com.amqo.facebookcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(
        HomeScreenState.Loading
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            Firebase.auth.currentUser?.let {
                _state.emit(
                    HomeScreenState.Loaded(
                        avatarUrl = it.photoUrl.toString()
                    )
                )
            } ?: kotlin.run {
                _state.emit(HomeScreenState.SignInRequired)
            }
        }
    }
}