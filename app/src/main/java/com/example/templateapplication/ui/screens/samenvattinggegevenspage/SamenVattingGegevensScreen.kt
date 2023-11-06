package com.example.templateapplication.ui.screens.samenvattinggegevenspage

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.AdresViewModel
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.model.klant.ContactGegevensViewModel
import com.example.templateapplication.ui.theme.DisabledButtonColor
import java.text.SimpleDateFormat

@Composable
fun SamenvattingGegevensScreen (
    modifier: Modifier = Modifier,
    gegevensViewModel: ContactGegevensViewModel = viewModel(),
    adresViewModel: AdresViewModel = viewModel(),
    formuleViewModel: FormuleViewModel = viewModel(),
    navigateEventGegevens:()->Unit,
    navigateContactGegevens:()->Unit,
) {
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        HeadOfPage()
        Navigation(
            navigateContactGegevens=navigateContactGegevens,
            navigateEventGegevens = navigateEventGegevens,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        EventGegevens(
            gegevensViewModel = gegevensViewModel,
            adresViewModel = adresViewModel,
            formuleViewModel = formuleViewModel,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        ContactGegevens(
            gegevensViewModel = gegevensViewModel,
            adresViewModel = adresViewModel
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        KostGegevens()
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
        ) {
            Text (text= "Offerte aanvragen")
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}
@Composable
fun HeadOfPage(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text= "Overzicht",
            textAlign = TextAlign.Center,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MainColor,
            progress = 1.00f,
            trackColor = MainLightestColor,
        )
    }
}
@Composable
fun Navigation (
    modifier: Modifier = Modifier,
    navigateEventGegevens:()->Unit,
    navigateContactGegevens:()->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text= "Ga naar",
            textAlign = TextAlign.Left,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            fontSize = 30.sp,
            color = Color.Black,
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Evenement") ,
                onClick = {
                    navigateEventGegevens()
                },
                style = TextStyle(fontSize = 22.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Contact en facturatie gegevens"),
                onClick = {
                    navigateContactGegevens()
                },
                style = TextStyle(fontSize = 22.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Extra materieel"),
                onClick = {},
                style = TextStyle(fontSize = 22.sp)
            )
        }

    }
}

@Composable
fun EventGegevens(
    modifier: Modifier = Modifier,
    adresViewModel: AdresViewModel,
    gegevensViewModel: ContactGegevensViewModel,
    formuleViewModel: FormuleViewModel,
) {
    val gegevensUiState by gegevensViewModel.gegevensUiState.collectAsState()
    val adresUiState by adresViewModel.adresUiState.collectAsState()
    val formuleUiState by formuleViewModel.formuleUiState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text= "Evenement",
            textAlign = TextAlign.Start,
            modifier= Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {
                Text(text="Datum",fontSize = 18.sp)},
            supportingContent = {
                Text(
                    text="${formuleUiState.beginDatum.time} - ${formuleUiState.eindDatum.time}",
                    fontSize = 16.sp)
                                },
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {
                Text(text="Locatie",fontSize = 18.sp)
                              },
            supportingContent = {
                Text(
                    text="",
                    fontSize = 16.sp)
                                },
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        
    }
}

@Composable
fun ContactGegevens(
    modifier: Modifier = Modifier,
    adresViewModel: AdresViewModel,
    gegevensViewModel: ContactGegevensViewModel
) {
    val gegevensUiState by gegevensViewModel.gegevensUiState.collectAsState()
    val adresUiState by adresViewModel.adresUiState.collectAsState()

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text= "Contact en facturatie gegevens",
            textAlign = TextAlign.Start,
            modifier= Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Naam",fontSize = 18.sp)},
            supportingContent = {Text(text=gegevensUiState.naam+" "+gegevensUiState.voornaam,fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Email",fontSize = 18.sp)},
            supportingContent = {Text(text=gegevensUiState.email,fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Telefoonnummer",fontSize = 18.sp)},
            supportingContent = {Text(text=gegevensUiState.telefoonnummer,fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Adres", fontSize = 18.sp)},
            supportingContent = {
                Text(
                    text=adresUiState.straat+" "+adresUiState.huisnummer+" "+adresUiState.gemeente,
                    fontSize = 16.sp
                )},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )

    }
}

@Composable
fun KostGegevens (
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text= "Kost",
            textAlign = TextAlign.Start,
            modifier= Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder))),
        ){
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Omschrijving",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="Prijs",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="Subtotaal",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="basic formule 1x",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€350",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€350",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Stoel 17x",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€5",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€85",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Bord 17x",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€5",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€85",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Vervoerskosten",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€0.75",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€2.25",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="TOTAAL EXCL BTW",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€2.25",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="TOTAAL BTW (21%)",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€2.25",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Te betalen",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€630",
                        textAlign = TextAlign.Start,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

    }
}