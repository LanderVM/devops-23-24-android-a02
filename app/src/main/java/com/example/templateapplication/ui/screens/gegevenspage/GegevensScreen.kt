package com.example.templateapplication.ui.screens.gegevenspage

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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R


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

    var facturatieAdressChecked by remember { mutableStateOf(true) }

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
        Spacer(modifier = Modifier.height(35.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.main))),
            progress = 0.50f,
            trackColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text="Personalia",
            color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.main))),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .height(17.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        ContactGegevens(
            naam = naam ,onNaamChange = {naam = it}, makeEmptyNaam = {naam = ""},
            voornaam = voornaam, onVoornaamChange = {voornaam = it}, makeEmptyVoornaam = {voornaam = ""},
            typeEvenement = typeEvenement, onTypeEvenementChange = {typeEvenement = it},makeEmptyTypeEvenement = {typeEvenement = ""},
            email = email, onEmailChange = {email = it},makeEmptyEmail = {email = ""},
        )
        Spacer(modifier = Modifier.height(20.dp))
        Adressering(
            straat = straat, onStraatChange = {straat = it},
            huisnummer = huisnummer, onHuisnummerChange = {huisnummer = it}, makeEmptyStraat = {straat = ""},
            gemeente = gemeente, onGemeenteChange = {gemeente = it}, makeEmptyHuisnummer = {huisnummer=""},
            postcode = postcode, onPostcodeChange = {postcode = it}, makeEmptyGemeente = {gemeente = ""},
            facturatieAdressChecked = facturatieAdressChecked, onFacturatieAdressCheckedChange = {facturatieAdressChecked = it}, makeEmptyPostcode = {postcode=""}
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button (
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.main))),
                disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.disabledButtonColor))),
                contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit)))
            ),
            enabled = buttonEnabled
        ) {
            Text (text= "Volgende")
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}



@Composable
fun ContactGegevens(
    modifier: Modifier = Modifier,
    naam:String,onNaamChange:(String)->Unit,makeEmptyNaam: () -> Unit,
    voornaam:String,onVoornaamChange:(String)->Unit,makeEmptyVoornaam: () -> Unit,
    typeEvenement:String,onTypeEvenementChange:(String)->Unit,makeEmptyTypeEvenement: () -> Unit,
    email:String,onEmailChange:(String)->Unit,makeEmptyEmail: () -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contact gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="Naam", value = naam, onChange = onNaamChange, makeEmpty = makeEmptyNaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Voornaam",value=voornaam, onChange = onVoornaamChange, makeEmpty = makeEmptyVoornaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Type Evenement",value = typeEvenement, onChange = onTypeEvenementChange, makeEmpty = makeEmptyTypeEvenement)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Email", value = email, onChange = onEmailChange, makeEmpty = makeEmptyEmail)
    }
}

@Composable
fun Adressering(
    modifier: Modifier = Modifier,
    straat:String,onStraatChange:(String)->Unit,makeEmptyStraat:()->Unit,
    huisnummer:String,onHuisnummerChange:(String)->Unit,makeEmptyHuisnummer:()->Unit,
    gemeente:String,onGemeenteChange:(String)->Unit,makeEmptyGemeente:()->Unit,
    postcode:String,onPostcodeChange:(String)->Unit,makeEmptyPostcode:()->Unit,
    facturatieAdressChecked:Boolean,onFacturatieAdressCheckedChange:(Boolean)->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Adres gegevens",textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="Straat", value = straat, onChange = onStraatChange, makeEmpty = makeEmptyStraat)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Huisnummer", value = huisnummer, onChange = onHuisnummerChange, makeEmpty = makeEmptyHuisnummer)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Gemeente", value = gemeente, onChange = onGemeenteChange, makeEmpty = makeEmptyGemeente)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Postcode", value = postcode, onChange = onPostcodeChange, makeEmpty = makeEmptyPostcode)
        Spacer(modifier = Modifier.height(15.dp))
        OptieFacturatieAdress(
            facturatieAdressChecked = facturatieAdressChecked,
            onFacturatieAdressCheckedChange = onFacturatieAdressCheckedChange
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(
    modifier: Modifier = Modifier,label:String,value: String,onChange:(String)->Unit,makeEmpty:()->Unit
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
        trailingIcon = {
            IconButton(
                onClick = makeEmpty,
                enabled = !value.isEmpty(),
                /*colors= IconButtonColors(
                    containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                    disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                ),*/
            ) {
            Icon(Icons.Default.Clear,contentDescription = null,tint=Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))))
        }},
    )
}
@Composable
fun OptieFacturatieAdress (
    modifier:Modifier = Modifier,
    facturatieAdressChecked:Boolean,
    onFacturatieAdressCheckedChange:(Boolean)->Unit,) {

    Row (
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = facturatieAdressChecked,
            onCheckedChange = onFacturatieAdressCheckedChange,
            /*colors = CheckboxColors(
                checkedBoxColor = Color(android.graphics.Color.parseColor("#D3B98B")),
                uncheckedBoxColor = Color(android.graphics.Color.parseColor("#D3B98B")),
                checkedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B")),
                uncheckedBorderColor = Color(android.graphics.Color.parseColor("#D3B98B")),
                checkedCheckmarkColor= Color(android.graphics.Color.parseColor("#FFFFFF")),,
                uncheckedCheckmarkColor= Color(android.graphics.Color.parseColor("#FFFFFF")),
                disabledCheckedBoxColor= Color(android.graphics.Color.parseColor("#D3B98B")),,
                disabledUncheckedBoxColor= Color(android.graphics.Color.parseColor("#D3B98B")),,
                disabledIndeterminateBoxColor= Color(android.graphics.Color.parseColor("#D3B98B")),,
                disabledBorderColor= Color(android.graphics.Color.parseColor("#D3B98B")),,
                disabledIndeterminateBorderColor= Color(android.graphics.Color.parseColor("#D3B98B")),
            ),*/
            colors = CheckboxDefaults.colors()
        )
        Text(text="Adres is ook facturatie adress", modifier = Modifier.padding(horizontal = 12.dp ))
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun FavoriteCollectionCardPreview() {
//    InputVeld(label = "naam")
//}

