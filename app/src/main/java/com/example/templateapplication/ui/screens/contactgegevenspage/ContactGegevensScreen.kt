package com.example.templateapplication.ui.screens.contactgegevenspage

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
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.templateapplication.R
import com.example.templateapplication.model.ContactGegevensViewModel
import com.example.templateapplication.navigation.NavigationRoutes
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor


@Composable
fun ConatctGegevensScreen (
    gegevensViewModel: ContactGegevensViewModel = viewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {

    // val gegevensUiState by gegevensViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    var facturatieAdressChecked by remember { mutableStateOf(gegevensViewModel.facturatieAdressChecked) }


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
            color = MainColor,
            progress = 0.50f,
            trackColor = MainLightestColor,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text="Personalia",
            color = MainColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .height(17.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))

        ContactGegevens(
            naam = gegevensViewModel.naam,
            onNaamChange = { gegevensViewModel.updateNaam(it) },
            makeEmptyNaam = { gegevensViewModel.updateNaam("") },
            voornaam = gegevensViewModel.voornaam,
            onVoornaamChange = { gegevensViewModel.updateVoornaam(it) },
            makeEmptyVoornaam = { gegevensViewModel.updateVoornaam("") },
            typeEvenement = gegevensViewModel.typeEvenement,
            onTypeEvenementChange = { gegevensViewModel.updateTypeEvenement(it) },
            makeEmptyTypeEvenement = { gegevensViewModel.updateTypeEvenement("") },
            email = gegevensViewModel.email,
            onEmailChange = { gegevensViewModel.updateEmail(it) },
            makeEmptyEmail = { gegevensViewModel.updateEmail("") },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Adressering(
            welkeAdressering = "Adres gegevens",
            straat = gegevensViewModel.straat,
            onStraatChange = { gegevensViewModel.updateStraat(it) },
            makeEmptyStraat = { gegevensViewModel.updateStraat("") },
            huisnummer = gegevensViewModel.huisnummer,
            onHuisnummerChange = { gegevensViewModel.updateHuisnummer(it) },
            makeEmptyHuisnummer = { gegevensViewModel.updateHuisnummer("") },
            gemeente = gegevensViewModel.gemeente,
            onGemeenteChange = { gegevensViewModel.updateGemeente(it) },
            makeEmptyGemeente = { gegevensViewModel.updateGemeente("") },
            postcode = gegevensViewModel.postcode,
            onPostcodeChange = { gegevensViewModel.updatePostcode(it) },
            makeEmptyPostcode = { gegevensViewModel.updatePostcode("") }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OptieFacturatieAdress(
            facturatieAdressChecked = gegevensViewModel.facturatieAdressChecked,
            onFacturatieAdressCheckedChange = {gegevensViewModel.updateFacturatieAdressChecked(it)}
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (!facturatieAdressChecked) {
            Adressering(
                welkeAdressering = "Facturatie adres",
                straat = gegevensViewModel.straatFacturatie,
                onStraatChange = { gegevensViewModel.updateStraatFacturatie(it) },
                makeEmptyStraat = { gegevensViewModel.updateStraatFacturatie("") },
                huisnummer = gegevensViewModel.huisnummerFacturatie,
                onHuisnummerChange = { gegevensViewModel.updateHuisnummerFacturatie(it) },
                makeEmptyHuisnummer = { gegevensViewModel.updateHuisnummerFacturatie("") },
                gemeente = gegevensViewModel.gemeenteFacturatie,
                onGemeenteChange = { gegevensViewModel.updateGemeenteFacturatie(it) },
                makeEmptyGemeente = { gegevensViewModel.updateGemeenteFacturatie("") },
                postcode = gegevensViewModel.postcodeFacturatie,
                onPostcodeChange = { gegevensViewModel.updatePostcodeFacturatie(it) },
                makeEmptyPostcode = { gegevensViewModel.updatePostcodeFacturatie("") }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button (
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = DisabledButtonColor,
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
            enabled = gegevensViewModel.allFieldsFilled()
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
    welkeAdressering:String,
    straat:String,onStraatChange:(String)->Unit,makeEmptyStraat:()->Unit,
    huisnummer:String,onHuisnummerChange:(String)->Unit,makeEmptyHuisnummer:()->Unit,
    gemeente:String,onGemeenteChange:(String)->Unit,makeEmptyGemeente:()->Unit,
    postcode:String,onPostcodeChange:(String)->Unit,makeEmptyPostcode:()->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = welkeAdressering,textAlign = TextAlign.Center,fontSize = 20.sp)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label="Straat", value = straat, onChange = onStraatChange, makeEmpty = makeEmptyStraat)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Huisnummer", value = huisnummer, onChange = onHuisnummerChange, makeEmpty = makeEmptyHuisnummer)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Gemeente", value = gemeente, onChange = onGemeenteChange, makeEmpty = makeEmptyGemeente)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Postcode", value = postcode, onChange = onPostcodeChange, makeEmpty = makeEmptyPostcode)
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
                colors= IconButtonDefaults.iconButtonColors(
                    containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                    contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                    disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                )
            ) {
                Icon(Icons.Default.Clear,contentDescription = null)
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
            colors = CheckboxDefaults.colors(
                checkedColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                uncheckedColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                checkmarkColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
            ),

            )
        Text(text="Adres is ook facturatie adress", modifier = Modifier.padding(horizontal = 12.dp ))
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun FavoriteCollectionCardPreview() {
//    InputVeld(label = "naam")
//}