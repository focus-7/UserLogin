package com.ceiba.login.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceiba.login.R
import com.ceiba.login.domain.entity.UserBuilder
import com.ceiba.login.presentation.viewmodel.UserViewModel
import kotlin.random.Random.Default.nextInt

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

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_image),
                contentDescription = stringResource(id = R.string.image_description)
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
            CustomText(
                text = stringResource(R.string.login),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp
            )

            Spacer(modifier = modifierSpacer20dp)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val isValidEmail = emailValue.value.count() > 5 && '@' in emailValue.value

                CustomOutlinedTextField(value = emailValue.value,
                    label = stringResource(id = R.string.email),
                    isError = !isValidEmail,
                    placeholder = stringResource(id = R.string.email),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = "icon_person")
                    },
                    onValueChange = {
                        emailValue.value = it
                        errorState.value = !isValidEmail
                    }
                )

                CustomOutlinedTextField(value = passwordValue.value,
                    label = stringResource(R.string.password),
                    placeholder = stringResource(R.string.password),
                    isError = passwordValue.value.isBlank(),
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
                                "password_eye",
                                tint = if (passwordVisibility.value) MaterialTheme.colors.primary else Color.Gray
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                Spacer(modifier = modifierSpacer10dp)

                CustomButton(
                    onClick = {
                        if (!errorState.value) {
                            val user = UserBuilder()
                                .email(emailValue.value)
                                .password(passwordValue.value)
                                .build()

                            userViewModel.loginUser(user)
                            navigateToHome(emailValue.value)
                        } else {
                            Toast.makeText(context,
                                R.string.user_password_incorrect,
                                Toast.LENGTH_LONG).show()
                        }
                    },
                    text = stringResource(R.string.enter_ok),
                    textUnit = 20.sp
                )

                Spacer(modifier = modifierSpacer10dp)

                OutlinedButton(
                    onClick = {
                        val nameUserRandom = "user_${randomUserName()}"

                        val guestUser = UserBuilder()
                            .name(nameUserRandom)
                            .build()

                        userViewModel.createGuestUser(guestUser)
                        navigateToHome(nameUserRandom)
                    },
                    modifier = modifierButton
                ) {
                    CustomText(text = stringResource(id = R.string.enter_as_guest), fontSize = 20.sp)
                }

                Spacer(modifier = modifierSpacer20dp)
                CustomText(
                    text = stringResource(id = R.string.create_an_account),
                    modifier = Modifier.clickable(onClick = {
                        navigateToRegister()
                    })
                )
                Spacer(modifier = modifierSpacer20dp)
            }
        }

    }
}

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomUserName() = (1..6)
    .map { nextInt(0, charPool.size) }
    .map(charPool::get)
    .joinToString("")



