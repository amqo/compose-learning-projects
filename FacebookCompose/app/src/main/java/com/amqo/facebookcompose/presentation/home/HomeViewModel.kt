package com.amqo.facebookcompose.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _mutableState = MutableStateFlow<HomeScreenState>(
        HomeScreenState.Loading
    )
    val state = _mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1_000)
            _mutableState.emit(HomeScreenState.SignInRequired)
//            delay(2_000)
//            _mutableState.emit(HomeScreenState.Loaded("https://static.jobscan.co/blog/uploads/hickman-testimonial-1.png"))
        }
    }
}