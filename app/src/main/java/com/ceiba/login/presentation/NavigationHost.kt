package com.ceiba.login.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.ceiba.login.composables.HomePage
import com.ceiba.login.composables.LoginPage
import com.ceiba.login.composables.RegisterPage
import com.ceiba.login.presentation.viewmodel.UserViewModel

@Composable
fun NavigationHost(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.Login.route) {
        composable(Destinations.Login.route) {
            LoginPage(
                navigateToRegister = {
                    navController.navigate(Destinations.Register.route)
                },
                navigateToHome = { email ->
                    navController.navigate(Destinations.Home.createRoute(email))
                },
                userViewModel
            )
        }

        composable(Destinations.Register.route) {
            RegisterPage(
                navigateToLogin = {
                    navController.navigate(Destinations.Login.route)
                },
                userViewModel
            )
        }

        composable(
            Destinations.Home.route,
            arguments = listOf(
                navArgument("userId") { defaultValue = Destinations.Home.route }
            )
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId")
            requireNotNull(userId)
            HomePage(userViewModel, navigateToLogin = {
                navController.navigate(Destinations.Login.route)
            }, userId)
        }
    }
}