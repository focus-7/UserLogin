package com.ceiba.login.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


val modifierSpacer20dp = Modifier.padding(20.dp)
val modifierSpacer10dp = Modifier.padding(10.dp)
val modifierButton = Modifier
    .fillMaxWidth(0.8f)
    .height(50.dp)


@Composable
fun CustomButton(
    onClick: () -> Unit,
    text: String,
    textUnit: TextUnit,
) {
    Button(
        onClick = onClick,
        modifier = modifierButton
    ) {
        CustomText(text = text, fontSize = textUnit)
    }
}


@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle(),
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
) {
    Text(text = text,
        style = style,
        modifier = modifier,
        fontSize = fontSize,
        textAlign = textAlign)
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    label: String,
    isError: Boolean = false,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        label = { Text(label) },
        isError = isError,
        placeholder = { Text(text = placeholder) },
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}