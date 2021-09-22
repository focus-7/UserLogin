package com.ceiba.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ceiba.login.domain.entity.User
import com.ceiba.login.domain.services.UserService
import com.ceiba.login.presentation.util.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun createUser(user: User) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            userService.createUser(user)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun createGuestUser(user: User) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            userService.createGuestUser(user)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }


    fun loginUser(user: User) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            userService.loginUser(user)
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun logOut() = userService.logOutUser()
}