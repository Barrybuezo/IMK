package com.example.imk.core.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun IMKTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    onValueChanged: (String) -> Unit,
    singleLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.medium,
    //KeyboardOptions
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(modifier = modifier, shape = shape, label = {
        IMKText(text = label)
    },
        value = value,
        onValueChange  = { onValueChanged(it)},
        singleLine = singleLine,
        keyboardOptions = keyboardOptions
    )
}