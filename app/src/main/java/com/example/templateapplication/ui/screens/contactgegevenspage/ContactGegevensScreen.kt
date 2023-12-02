package com.example.templateapplication.ui.screens.contactgegevenspage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.SeperatingTitle

@Composable
fun ConatctGegevensScreen(
    modifier: Modifier = Modifier,
    gegevensViewModel: ContactGegevensViewModel = viewModel(),
    adresViewModel: AdresViewModel = viewModel(),
    navigateExtras: () -> Unit,
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
            text = stringResource(id = R.string.contactDetails_personal_info),
            progression = 0.50f,
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
            welkeAdressering = stringResource(id = R.string.contactDetails_address_info),
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
            btwNummer= adresUiState.btwNummer,
            onBtwNummerChange= {adresViewModel.updateBtwNummer(it)},
            makeEmptyBtwNummer= {adresViewModel.updateBtwNummer("")},
        )
        Spacer(modifier = Modifier.height(15.dp))

        Spacer(modifier = Modifier.height(15.dp))

        Spacer(modifier = Modifier.height(30.dp))
        NextPageButton(
            navigeer = navigateExtras,
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
        SeperatingTitle(
            text = stringResource(id = R.string.contactDetails_contact_info),
        )
        InputVeld(label = stringResource(id = R.string.contactDetails_first_name), value = voornaam, onChange = onVoornaamChange, makeEmpty = makeEmptyVoornaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_name), value = naam, onChange = onNaamChange, makeEmpty = makeEmptyNaam)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_phone_number), value = telefoonnummer, onChange = onTelefoonnummerChange, makeEmpty = makeEmptyTelefoonnummer)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_email), value = email, onChange = onEmailChange, makeEmpty = makeEmptyEmail)
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
    btwNummer: String,
    onBtwNummerChange: (String) -> Unit,
    makeEmptyBtwNummer: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SeperatingTitle(
            text = welkeAdressering,
        )
        InputVeld(label = stringResource(id = R.string.contactDetails_street), value = straat, onChange = onStraatChange, makeEmpty = makeEmptyStraat)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_house_number), value = huisnummer, onChange = onHuisnummerChange, makeEmpty = makeEmptyHuisnummer)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_city), value = gemeente, onChange = onGemeenteChange, makeEmpty = makeEmptyGemeente)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_postal_code), value = postcode, onChange = onPostcodeChange, makeEmpty = makeEmptyPostcode)
        Spacer(modifier = Modifier.height(20.dp))
        InputVeld(label = stringResource(id = R.string.contactDetails_vat_number), value = btwNummer, onChange = onBtwNummerChange, makeEmpty = makeEmptyBtwNummer)
    }
}

@Composable
fun InputVeld(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    makeEmpty: () -> Unit,
) {
    OutlinedTextField(
        label = { Text(text = label, color = Color(0xFFe9dcc5)) },
        value = value,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFe9dcc5),
            unfocusedBorderColor = Color(0xFFe9dcc5),
        ),
        trailingIcon = {
            IconButton(
                onClick = makeEmpty,
                enabled = value.isNotEmpty(),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFFe9dcc5),
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                ),
            ) {
                Icon(Icons.Default.Clear, contentDescription = null)
            }
        },
    )
}
