package com.example.templateapplication.ui.screens.contactgegevenspage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.AdresViewModel
import com.example.templateapplication.model.klant.ContactGegevensViewModel
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.Titel
import com.example.templateapplication.ui.commons.VolgendeKnop

@Composable
fun ConatctGegevensScreen(
    gegevensViewModel: ContactGegevensViewModel = viewModel(),
    adresViewModel: AdresViewModel = viewModel(),
    navigateSamenvatting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val gegevensUiState by gegevensViewModel.gegevensUiState.collectAsState()
    val adresUiState by adresViewModel.adresUiState.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProgressieBar(
            text = "Personalia",
            progressie = 0.50f,
        )
        ContactGegevens(
            naam = gegevensUiState.naam,
            onNaamChange = { gegevensViewModel.updateNaam(it) },
            makeEmptyNaam = { gegevensViewModel.updateNaam("") },
            voornaam = gegevensUiState.voornaam,
            onVoornaamChange = { gegevensViewModel.updateVoornaam(it) },
            makeEmptyVoornaam = { gegevensViewModel.updateVoornaam("") },
            telefoonnummer = gegevensUiState.telefoonnummer,
            onTelefoonnummerChange = { gegevensViewModel.updateTelefoonnummer(it) },
            makeEmptyTelefoonnummer = { gegevensViewModel.updateTelefoonnummer("") },
            email = gegevensUiState.email,
            onEmailChange = { gegevensViewModel.updateEmail(it) },
            makeEmptyEmail = { gegevensViewModel.updateEmail("") },
        )
        Spacer(modifier = Modifier.height(30.dp))

        Adressering(
            welkeAdressering = "Adres gegevens",
            straat = adresUiState.straat,
            onStraatChange = { adresViewModel.updateStraat(it) },
            makeEmptyStraat = { adresViewModel.updateStraat("") },
            huisnummer = adresUiState.huisnummer,
            onHuisnummerChange = { adresViewModel.updateHuisnummer(it) },
            makeEmptyHuisnummer = { adresViewModel.updateHuisnummer("") },
            gemeente = adresUiState.gemeente,
            onGemeenteChange = { adresViewModel.updateGemeente(it) },
            makeEmptyGemeente = { adresViewModel.updateGemeente("") },
            postcode = adresUiState.postcode,
            onPostcodeChange = { adresViewModel.updatePostcode(it) },
            makeEmptyPostcode = { adresViewModel.updatePostcode("") },
        )
        Spacer(modifier = Modifier.height(15.dp))

        OptieFacturatieAdress(
            facturatieAdressChecked = adresUiState.facturatieAdressChecked,
            onFacturatieAdressCheckedChange = { adresViewModel.updateFacturatieAdressChecked(it) },
        )
        Spacer(modifier = Modifier.height(15.dp))

        if (!adresUiState.facturatieAdressChecked) {
            Adressering(
                welkeAdressering = "Facturatie adres",
                straat = adresUiState.straatFacturatie,
                onStraatChange = { adresViewModel.updateStraatFacturatie(it) },
                makeEmptyStraat = { adresViewModel.updateStraatFacturatie("") },
                huisnummer = adresUiState.huisnummerFacturatie,
                onHuisnummerChange = { adresViewModel.updateHuisnummerFacturatie(it) },
                makeEmptyHuisnummer = { adresViewModel.updateHuisnummerFacturatie("") },
                gemeente = adresUiState.gemeenteFacturatie,
                onGemeenteChange = { adresViewModel.updateGemeenteFacturatie(it) },
                makeEmptyGemeente = { adresViewModel.updateGemeenteFacturatie("") },
                postcode = adresUiState.postcodeFacturatie,
                onPostcodeChange = { adresViewModel.updatePostcodeFacturatie(it) },
                makeEmptyPostcode = { adresViewModel.updatePostcodeFacturatie("") },
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        VolgendeKnop(
            navigeer = navigateSamenvatting,
            enabled = gegevensViewModel.allFieldsFilled() && adresViewModel.allFieldsFilled(),
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ContactGegevens(
    modifier: Modifier = Modifier,
    naam: String,
    onNaamChange: (String) -> Unit,
    makeEmptyNaam: () -> Unit,
    voornaam: String,
    onVoornaamChange: (String) -> Unit,
    makeEmptyVoornaam: () -> Unit,
    telefoonnummer: String,
    onTelefoonnummerChange: (String) -> Unit,
    makeEmptyTelefoonnummer: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    makeEmptyEmail: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Titel(
            text = "Contact gegevens",
        )
        InputVeld(label = "Naam", value = naam, onChange = onNaamChange, makeEmpty = makeEmptyNaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Voornaam", value = voornaam, onChange = onVoornaamChange, makeEmpty = makeEmptyVoornaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Telefoonnummer", value = telefoonnummer, onChange = onTelefoonnummerChange, makeEmpty = makeEmptyTelefoonnummer)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = "Email", value = email, onChange = onEmailChange, makeEmpty = makeEmptyEmail)
    }
}

@Composable
fun Adressering(
    modifier: Modifier = Modifier,
    welkeAdressering: String,
    straat: String,
    onStraatChange: (String) -> Unit,
    makeEmptyStraat: () -> Unit,
    huisnummer: String,
    onHuisnummerChange: (String) -> Unit,
    makeEmptyHuisnummer: () -> Unit,
    gemeente: String,
    onGemeenteChange: (String) -> Unit,
    makeEmptyGemeente: () -> Unit,
    postcode: String,
    onPostcodeChange: (String) -> Unit,
    makeEmptyPostcode: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Titel(
            text = welkeAdressering,
        )
        InputVeld(label = "Straat", value = straat, onChange = onStraatChange, makeEmpty = makeEmptyStraat)
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
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    makeEmpty: () -> Unit,
) {
    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))) },
        value = value,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
        ),
        trailingIcon = {
            IconButton(
                onClick = makeEmpty,
                enabled = !value.isEmpty(),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                    contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                    disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                ),
            ) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
        },
    )
}

@Composable
fun OptieFacturatieAdress(
    modifier: Modifier = Modifier,
    facturatieAdressChecked: Boolean,
    onFacturatieAdressCheckedChange: (Boolean) -> Unit,
) {
    Row(
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
        Text(text = "Adres is ook facturatie adress", modifier = Modifier.padding(horizontal = 12.dp))
    }
}

// @Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
// @Composable
// fun FavoriteCollectionCardPreview() {
//    InputVeld(label = "naam")
// }
