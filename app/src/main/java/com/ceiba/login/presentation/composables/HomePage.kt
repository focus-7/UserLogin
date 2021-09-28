package com.ceiba.login.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ceiba.login.presentation.util.LoadingState
import com.ceiba.login.presentation.viewmodel.UserViewModel

@Composable
fun HomePage(userViewModel: UserViewModel, navigateToLogin: () -> Unit, email: String) {
    val state by userViewModel.loadingState.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            backgroundColor = Color.White,
            elevation = 1.dp,
            title = {
                Text(text = "Bienvenido")
            },
            actions = {
                IconButton(onClick = {
                    userViewModel.logOut()
                    navigateToLogin()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ExitToApp,
                        contentDescription = null,
                    )
                }
            }
        )

        CustomText(
            text = "Correo: $email",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )

        if (state.status == LoadingState.Status.RUNNING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}





