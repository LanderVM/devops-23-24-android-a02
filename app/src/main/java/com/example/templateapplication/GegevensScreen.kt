package com.example.templateapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun GegevensScreen (modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ContactGegevens()
        Spacer(modifier = Modifier.height(20.dp))
        Adressering()
        Spacer(modifier = Modifier.height(15.dp))
        Button (
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit)))
            ),
        ) {
            Text (text= "volgende")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}



@Composable
fun ContactGegevens(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contact gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Adress gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="straat")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "huisnummer")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "gemeente")
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "postcode")
        Spacer(modifier = Modifier.height(15.dp))
        OptieFacturatieAdress()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(modifier: Modifier = Modifier,label:String) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))) },
        value = "",
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))
        ),
        //trailingIcon = (Icon(Icons.Default.Clear,contentDescription = null)),
    )
}
@Composable
fun OptieFacturatieAdress (modifier:Modifier = Modifier) {

    Row (
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = true,
            onCheckedChange = {},
            //colors = CheckboxColors(
            //    checkedBoxColor = Color(android.graphics.Color.parseColor("#D3B98B")),
            //    uncheckedBoxColor = Color(android.graphics.Color.parseColor("#D3B98B")),
            //    checkedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B")),
            //    unCheckedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B")),
            //),
        )
        Text(text="Adress is ook facturatie adress", modifier = Modifier.padding(horizontal = 20.dp ))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavoriteCollectionCardPreview() {
    InputVeld(label = "naam")
}


