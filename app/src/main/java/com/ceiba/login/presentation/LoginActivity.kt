package com.ceiba.login.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.ceiba.login.presentation.viewmodel.UserViewModel

import com.ceiba.login.ui.theme.LoginTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userViewModel: UserViewModel by viewModels()
        setContent {
            LoginTheme {
                NavigationHost(userViewModel)
            }
        }
    }
}