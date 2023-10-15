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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.RegExp


@Composable
fun GegevensScreen (modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    var naam by remember { mutableStateOf("") }
    var voornaam by remember { mutableStateOf("") }
    var typeEvenement by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var straat by remember { mutableStateOf("") }
    var huisnummer by remember { mutableStateOf("") }
    var gemeente by remember { mutableStateOf("") }
    var postcode by remember { mutableStateOf("") }

    val buttonEnabled:Boolean
    if (naam.isBlank()||naam.isEmpty()||voornaam.isBlank()||voornaam.isEmpty()||typeEvenement.isBlank()||
        typeEvenement.isEmpty()||email.isBlank()||email.isEmpty()||
        straat.isBlank()||straat.isEmpty()||huisnummer.isBlank()||huisnummer.isEmpty()||
        gemeente.isBlank()||gemeente.isEmpty()||postcode.isBlank()||postcode.isEmpty()) {
        buttonEnabled = false
    } else {
        buttonEnabled = true
    }


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ContactGegevens(
            naam = naam ,onNaamChange = {naam = it},
            voornaam = voornaam, onVoornaamChange = {voornaam = it},
            typeEvenement = typeEvenement, onTypeEvenementChange = {typeEvenement = it},
            email = email, onEmailChange = {email = it}
        )
        Spacer(modifier = Modifier.height(20.dp))
        Adressering(
            straat = straat, onStraatChange = {straat = it},
            huisnummer = huisnummer, onHuisnummerChange = {huisnummer = it},
            gemeente = gemeente, onGemeenteChange = {gemeente = it},
            postcode = postcode, onPostcodeChange = {postcode = it},
        )
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
            enabled = buttonEnabled
        ) {
            Text (text= "volgende")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}



@Composable
fun ContactGegevens(
    modifier: Modifier = Modifier,
    naam:String,onNaamChange:(String)->Unit,
    voornaam:String,onVoornaamChange:(String)->Unit,
    typeEvenement:String,onTypeEvenementChange:(String)->Unit,
    email:String,onEmailChange:(String)->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contact gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="naam", value = naam, onChange = onNaamChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "voornaam",value=voornaam, onChange = onVoornaamChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Type Evenement",value = typeEvenement, onChange = onTypeEvenementChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Email", value = email, onChange = onEmailChange)
    }
}

@Composable
fun Adressering(
    modifier: Modifier = Modifier,
    straat:String,onStraatChange:(String)->Unit,
    huisnummer:String,onHuisnummerChange:(String)->Unit,
    gemeente:String,onGemeenteChange:(String)->Unit,
    postcode:String,onPostcodeChange:(String)->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Adress gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="straat", value = straat, onChange = onStraatChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "huisnummer", value = huisnummer, onChange = onHuisnummerChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "gemeente", value = gemeente, onChange = onGemeenteChange)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "postcode", value = postcode, onChange = onPostcodeChange)
        Spacer(modifier = Modifier.height(15.dp))
        OptieFacturatieAdress()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(
    modifier: Modifier = Modifier,label:String,value: String,onChange:(String)->Unit
) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))) },
        value = value,
        onValueChange =onChange,
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

//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun FavoriteCollectionCardPreview() {
//    InputVeld(label = "naam")
//}


