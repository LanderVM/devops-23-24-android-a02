package com.example.templateapplication.ui.commons

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.templateapplication.ui.theme.BlancheTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun AlertPopUp(
    onDismissRequest: () -> Unit,
    onConfirmation: (() -> Unit)? = null,
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    dismissText: String,
) {
    BlancheTheme(isOnline = true) {
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