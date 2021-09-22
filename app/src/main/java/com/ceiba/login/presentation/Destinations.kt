package com.ceiba.login.presentation

sealed class Destinations(
    val route: String,
) {
    object Login : Destinations("login")
    object Register : Destinations("register")
    object Home : Destinations("home/?userId={userId}") {
        fun createRoute(email: String) = "home/?userId=$email"
    }
}