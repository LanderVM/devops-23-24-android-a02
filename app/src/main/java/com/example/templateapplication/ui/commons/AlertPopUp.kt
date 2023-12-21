package com.example.templateapplication.ui.commons

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.templateapplication.ui.theme.BlancheTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Displays an alert dialog with customizable title, text, and buttons.
 *
 * This Composable function renders an alert dialog using the Blanche theme.
 * It provides options for confirmation and dismissal actions, which are executed when
 * the corresponding buttons are clicked.
 *
 * @param onDismissRequest A lambda function that is called when the dismiss action is triggered.
 * @param onConfirmation An optional lambda function that is called when the confirm action is triggered. Defaults to null.
 * @param dialogTitle The title text of the dialog.
 * @param dialogText The main content text of the dialog.
 * @param confirmText The text for the confirmation button.
 * @param dismissText The text for the dismiss button.
 */
@Composable
fun AlertPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: (() -> Unit)? = null,
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    dismissText: String,
) {
    BlancheTheme {
        AlertDialog(
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                onConfirmation?.let {
                    TextButton(
                        onClick = {
                            onConfirmation()
                        }
                    ) {
                        Text(text = confirmText)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(text = dismissText)
                }
            },
        )
    }
}
@Preview
@Composable
fun PopUpPreview(
    modifier: Modifier = Modifier
) {
    AlertPopUp(onDismissRequest = { }, dialogTitle = "Titel", dialogText = "Tekst...", confirmText = "Confirm txt", dismissText = "Dismiss txt")
}