package com.ceiba.login.composables

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceiba.login.R
import com.ceiba.login.domain.entity.User
import com.ceiba.login.presentation.viewmodel.UserViewModel

@Composable
fun LoginPage(
    navigateToRegister: () -> Unit,
    navigateToHome: (String) -> Unit,
    userViewModel: UserViewModel,
) {
    val context = LocalContext.current

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val errorState = remember { mutableStateOf(true) }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_image),
                contentDescription = "image_login"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .verticalScroll(rememberScrollState())
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = "Ingreso",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val isValidEmail = emailValue.value.count() > 5 && '@' in emailValue.value
                OutlinedTextField(
                    value = emailValue.value,
                    onValueChange = {
                        emailValue.value = it
                        errorState.value = !isValidEmail
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "icon_person")
                    },
                    label = { Text("Correo") },
                    isError = !isValidEmail,
                    placeholder = { Text(text = "Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = {
                        passwordValue.value = it
                        errorState.value = passwordValue.value.isBlank()
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.password_eye),
                                contentDescription = "password_eye",
                                tint = if (passwordVisibility.value) Color.Magenta else Color.Gray
                            )
                        }
                    },
                    label = { Text("Contraseña") },
                    isError = passwordValue.value.isBlank(),
                    placeholder = { Text(text = "Contraseña") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .focusRequester(focusRequester = focusRequester),
                )

                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (!errorState.value) {
                            userViewModel.loginUser(
                                User.UserBuilder()
                                    .email(emailValue.value)
                                    .password(passwordValue.value)
                                    .build()
                            )
                            navigateToHome(emailValue.value)
                        } else {
                            Toast.makeText(context,
                                R.string.user_password_incorrect,
                                Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Ingresar", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    onClick = {
                        userViewModel.createGuestUser(
                            User.UserBuilder()
                                .name("Invitado 123")
                                .build()
                        )
                        navigateToHome(emailValue.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Ingresar como invitado", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Crear una cuenta",
                    modifier = Modifier.clickable(onClick = {
                        navigateToRegister()
                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }
        }

    }
}



