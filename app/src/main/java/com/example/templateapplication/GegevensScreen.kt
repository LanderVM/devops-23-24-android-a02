package com.example.templateapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun GegevensScreen (modifier: Modifier = Modifier) {

    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        ContactGegevens()
        Adressering()
    }
}



@Composable
fun ContactGegevens(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Contact gegevens",textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="naam")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "voornaam")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Type Evenement")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Email")
    }
}

@Composable
fun Adressering() {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Adress gegevens",textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="straat")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "huisnummer")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "gemeente")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "postcode")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(modifier: Modifier = Modifier,label:String) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor("#D3B98B"))) },
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth().defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B")),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B"))),
        //trailingIcon = (Icon(Icons.Default.Clear,contentDescription = null)),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionCardPreview() {
    InputVeld(label = "naam")
}


