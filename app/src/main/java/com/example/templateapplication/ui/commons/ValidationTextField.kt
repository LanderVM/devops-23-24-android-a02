package com.example.templateapplication.ui.commons
import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.templateapplication.R
import com.example.templateapplication.model.UiText

@Composable
fun ValidationTextFieldApp(
    modifier: Modifier = Modifier,
    placeholder: String,
    text: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    errorMessage: UiText? = null,
    isError: Boolean = false,
    isVisible: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    maxLines: Int = 1
) {
    val isKeyboardTypeNumber = keyboardType == KeyboardType.Phone || keyboardType == KeyboardType.Number
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val displayError = isError && errorMessage != null

    val colorBorder = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    }

    Column(modifier) {
        val context = LocalContext.current
        OutlinedTextField(
            label = { Text(text = placeholder) },
            value = text,
            onValueChange = { newInput ->
                onValueChange(
                    if (isKeyboardTypeNumber) newInput.filter { it.isDigit() || it == '+' }
                    else newInput
                )
            },
            maxLines = maxLines,
            singleLine = singleLine,
            interactionSource = interactionSource,
            visualTransformation = when {
                keyboardType == KeyboardType.Password && !isVisible -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            isError = displayError,
            trailingIcon = {
                if (text.isNotEmpty()) {
                    ClearTextIconButton(onValueChange)
                }
            }
        )
        ErrorMessage(errorMessage, displayError, context)
    }
}

@Composable
private fun ClearTextIconButton(onValueChange: (String) -> Unit) {
    IconButton(onClick = { onValueChange("") }) {
        Icon(Icons.Default.Clear, contentDescription = stringResource(id = R.string.clearableOutlinedTextField_iconDescription))
    }
}

@Composable
private fun ErrorMessage(errorMessage: UiText?, displayError: Boolean, context: Context) {
    if (displayError) {
        Text(
            text = errorMessage!!.asString(context),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

