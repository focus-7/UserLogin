package com.ceiba.login.composables

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceiba.login.R
import com.ceiba.login.domain.entity.User
import com.ceiba.login.presentation.util.LoadingState
import com.ceiba.login.presentation.util.alert
import com.ceiba.login.presentation.util.positiveButton
import com.ceiba.login.presentation.viewmodel.UserViewModel

@Composable
fun RegisterPage(navigateToLogin: () -> Unit, userViewModel: UserViewModel) {

    val context = LocalContext.current

    val nameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }
    val phoneValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf(true) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val stateLoading by userViewModel.loadingState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.register_page),
                contentDescription = "register_image"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registro", fontSize = 30.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            )
            Spacer(modifier = Modifier.padding(20.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = nameValue.value,
                    onValueChange = { nameValue.value = it },
                    label = { Text(text = "Nombre (opcional)") },
                    placeholder = { Text(text = "Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                val isValidEmail = emailValue.value.count() > 5 && '@' in emailValue.value
                OutlinedTextField(
                    value = emailValue.value,
                    onValueChange = {
                        emailValue.value = it
                        errorState.value = !isValidEmail
                    },
                    label = { Text("Correo") },
                    isError = !isValidEmail,
                    placeholder = { Text(text = "Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                OutlinedTextField(
                    value = phoneValue.value,
                    onValueChange = { phoneValue.value = it },
                    label = { Text(text = "Teléfono (opcional)") },
                    placeholder = { Text(text = "Teléfono") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = {
                        passwordValue.value = it
                        errorState.value =
                            !validatePassword(passwordValue.value, confirmPasswordValue.value)
                    },
                    label = { Text("Contraseña") },
                    isError = !validatePassword(passwordValue.value, confirmPasswordValue.value),
                    placeholder = { Text(text = "Contraseña") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.password_eye),
                                "password_eye",
                                tint = if (passwordVisibility.value) MaterialTheme.colors.primary else Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                OutlinedTextField(
                    value = confirmPasswordValue.value,
                    onValueChange = {
                        confirmPasswordValue.value = it
                        errorState.value =
                            !validatePassword(passwordValue.value, confirmPasswordValue.value)
                    },
                    label = { Text("Confirmar contraseña") },
                    isError = !validatePassword(passwordValue.value, confirmPasswordValue.value),
                    placeholder = { Text(text = "Confirmar contraseña") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.password_eye),
                                "password_eye",
                                tint = if (confirmPasswordVisibility.value) MaterialTheme.colors.primary else Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Button(onClick = {
                    if (!errorState.value) {
                        userViewModel.createUser(
                            User.UserBuilder()
                                .name(nameValue.value)
                                .email(emailValue.value)
                                .password(passwordValue.value)
                                .phone(phoneValue.value)
                                .build()
                        )
                    } else {
                        Toast.makeText(context,
                            R.string.user_password_incorrect,
                            Toast.LENGTH_LONG).show()
                    }
                }, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                ) {
                    Text(text = "Registrarse", fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Text(
                    text = "Regresar al Login",
                    modifier = Modifier.clickable(onClick = {
                        navigateToLogin()
                    })
                )

                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
    when (stateLoading.status) {

        LoadingState.Status.SUCCESS -> {
            context.alert {
                setTitle(R.string.user_register)
                setMessage(R.string.user_created)
                positiveButton {
                    userViewModel.loadingState.value = LoadingState.IDLE
                    navigateToLogin()
                }
            }
        }
        LoadingState.Status.FAILED -> {
            context.alert {
                setTitle(R.string.error)
                setMessage(R.string.user_exists)
                positiveButton {
                    userViewModel.loadingState.value = LoadingState.IDLE
                }
            }
        }
        else -> {
        }
    }
}

fun validatePassword(password: String, confirmPassword: String) =
    password == confirmPassword && password.isNotBlank() && password.length >= 6

