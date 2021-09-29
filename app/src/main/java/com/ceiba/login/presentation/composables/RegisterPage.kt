package com.ceiba.login.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceiba.login.R
import com.ceiba.login.domain.entity.UserBuilder
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

            CustomText(text = stringResource(id = R.string.register),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                )
            )

            Spacer(modifier = modifierSpacer20dp)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CustomOutlinedTextField(
                    value = nameValue.value,
                    label = stringResource(id = R.string.name_optional),
                    placeholder = stringResource(id = R.string.name),
                    onValueChange = { nameValue.value = it },
                )

                val isValidEmail = emailValue.value.count() > 5 && '@' in emailValue.value

                CustomOutlinedTextField(value = emailValue.value,
                    label = stringResource(id = R.string.email),
                    isError = !isValidEmail,
                    placeholder = stringResource(id = R.string.email),
                    onValueChange = {
                        emailValue.value = it
                        errorState.value = !isValidEmail
                    }
                )

                CustomOutlinedTextField(value = phoneValue.value,
                    label = stringResource(id = R.string.telephone_optional),
                    placeholder = stringResource(id = R.string.telephone),
                    keyboardType = KeyboardType.Phone,
                    onValueChange = {
                        phoneValue.value = it
                    }
                )

                CustomOutlinedTextField(value = passwordValue.value,
                    label = stringResource(id = R.string.password),
                    placeholder = stringResource(id = R.string.password),
                    isError = !validatePassword(passwordValue.value, confirmPasswordValue.value),

                    onValueChange = {
                        passwordValue.value = it
                        errorState.value =
                            !validatePassword(passwordValue.value, confirmPasswordValue.value)
                    },
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

                CustomOutlinedTextField(value = confirmPasswordValue.value,
                    label = stringResource(id = R.string.confirm_password),
                    placeholder = stringResource(id = R.string.confirm_password),
                    isError = !validatePassword(passwordValue.value, confirmPasswordValue.value),
                    onValueChange = {
                        confirmPasswordValue.value = it
                        errorState.value =
                            !validatePassword(passwordValue.value, confirmPasswordValue.value)
                    },
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

                Spacer(modifier = modifierSpacer10dp)

                CustomButton(
                    onClick = {
                        if (!errorState.value) {
                            val user = UserBuilder()
                                .name(nameValue.value)
                                .email(emailValue.value)
                                .password(passwordValue.value)
                                .phone(phoneValue.value)
                                .build()

                            userViewModel.createUser(user)
                        } else {
                            Toast.makeText(context,
                                R.string.user_password_incorrect,
                                Toast.LENGTH_LONG).show()
                        }
                    },
                    text = stringResource(R.string.button_register),
                    textUnit = 20.sp
                )

                Spacer(modifier = modifierSpacer20dp)

                CustomText(
                    text = stringResource(id = R.string.return_to_login),
                    modifier = Modifier.clickable(onClick = {
                        navigateToLogin()
                    })
                )

                Spacer(modifier = modifierSpacer20dp)
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

