package com.amqo.facebookcompose.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {

    private val _mutableState = MutableStateFlow<SignInState>(
        SignInState.Pending
    )
    val state = _mutableState.asStateFlow()

    init {
        viewModelScope.launch {
//            delay(2_000)
//            _mutableState.emit(SignInState.Completed)
        }
    }
}