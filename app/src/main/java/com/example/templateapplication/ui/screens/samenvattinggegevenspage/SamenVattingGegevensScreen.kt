package com.example.templateapplication.ui.screens.samenvattinggegevenspage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.AdresViewModel
import com.example.templateapplication.model.extraMateriaal.ExtraItemState
import com.example.templateapplication.model.extraMateriaal.ExtraItemViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.model.klant.ContactGegevensViewModel
import com.example.templateapplication.ui.screens.QuotationViewModel
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor


@Composable
fun SamenvattingGegevensScreen (
    modifier: Modifier = Modifier,
    gegevensViewModel: ContactGegevensViewModel = viewModel(),
    adresViewModel: AdresViewModel = viewModel(),
    formulaViewModel: FormulaViewModel = viewModel(),
    extraItemViewModel: ExtraItemViewModel = viewModel(),
    eventAddressViewModel: QuotationViewModel = viewModel(factory = QuotationViewModel.Factory),
    navigateEventGegevens: ()->Unit,
    navigateContactGegevens:()->Unit,
    navigateExtras: () -> Unit,
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
            navigateExtras = navigateExtras
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        EventGegevens(
            gegevensViewModel = gegevensViewModel,
            adresViewModel = adresViewModel,
            formulaViewModel = formulaViewModel,
            eventAddressViewModel = eventAddressViewModel,
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        ContactGegevens(
            gegevensViewModel = gegevensViewModel,
            adresViewModel = adresViewModel
        )
        Spacer(modifier = Modifier.height(30.dp))

        if(!extraItemViewModel.getListAddedItems().isEmpty()){
            Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
            Spacer(modifier = Modifier.height(30.dp))
            ExtrasScreen (extraItemViewModel = extraItemViewModel)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        KostGegevens(extraItemViewModel= extraItemViewModel, eventAddressViewModel = eventAddressViewModel)
        Spacer(modifier = Modifier.height(30.dp))
        Button (
            onClick = {

            },
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
    navigateExtras: () -> Unit,
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
                painter = painterResource(id = com.example.templateapplication.R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Extra materiaal"),
                onClick = {navigateExtras()},
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
    formulaViewModel: FormulaViewModel,
    eventAddressViewModel: QuotationViewModel = viewModel(factory = QuotationViewModel.Factory),
    ) {
    val gegevensUiState by gegevensViewModel.gegevensUiState.collectAsState()
    val adresUiState by adresViewModel.adresUiState.collectAsState()
    val formuleUiState by formulaViewModel.formulaUiState.collectAsState()

    val googleMapsPredictionState by eventAddressViewModel.uiStatePrediction.collectAsState()
    val googleMapsPredictionApiState = eventAddressViewModel.googleMapsPredictionApiState

    val googleMapsPlaceState by eventAddressViewModel.uiStatePlace.collectAsState()
    val googleMapsPlaceApiState = eventAddressViewModel.googleMapsPlaceApiState

    val googleMapsDistanceState by eventAddressViewModel.uiStateDistance.collectAsState()
    val googleMapsDistanceApiState = eventAddressViewModel.googleMapsDistanceApiState


    var show by rememberSaveable { mutableStateOf(true) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text= "Evenement",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainColor,
            )
            IconButton(onClick = { show = !show}) {
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "dropdown")
            }
        }
        if(show){
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {
                    Text(text="Datum",fontSize = 18.sp)},
                supportingContent = {
                    Text(
                        text=formulaViewModel.getDateRange(),
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
                        text=googleMapsPlaceState.placeResponse.candidates[0].formatted_address,
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
}

@Composable
fun ContactGegevens(
    modifier: Modifier = Modifier,
    adresViewModel: AdresViewModel,
    gegevensViewModel: ContactGegevensViewModel
) {
    val gegevensUiState by gegevensViewModel.gegevensUiState.collectAsState()
    val adresUiState by adresViewModel.adresUiState.collectAsState()
    var show by rememberSaveable { mutableStateOf(true) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text= "Contact en facturatie gegevens",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainColor,
            )
            IconButton(onClick = { show = !show}) {
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "dropdown")
            }

        }
        if(show){
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
            )}


    }
}

@Composable
fun ExtrasScreen(
    modifier: Modifier = Modifier,
    extraItemViewModel: ExtraItemViewModel = viewModel(),

) {
    var show by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text= "Extra Materiaal",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = MainColor,
            )
            IconButton(onClick = { show = !show}) {
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "dropdown")
            }
        }
        if(show){
            extraItemViewModel.getListAddedItems().forEach { extraItem ->
                ExtraItemCard(
                    extraItem = extraItem,
                    onAmountChanged = {extraItem, amount ->
                        extraItemViewModel.changeExtraItemAmount(extraItem, amount)},
                    onRemoveItem= { extraItemViewModel.removeItemFromCart(extraItem) },
                    modifier = Modifier.padding(8.dp)
                )

            }
        }

        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraItemCard(
    extraItem: ExtraItemState,
    onAmountChanged: (ExtraItemState, Int) -> Unit,
    onRemoveItem: (ExtraItemState) -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(containerColor = MainLightestColor, contentColor = Color.Black, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image
            Image(
                painter = painterResource(id = R.drawable.foto7), // Replace with actual image
                contentDescription = null, // Content description can be set based on your use case
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Item Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    modifier = Modifier.size(170.dp, 40.dp),
                    text = "${extraItem.title}",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // Price
                Text(
                    text = "$${extraItem.price}",
                    style = MaterialTheme.typography.headlineSmall,

                    )
            }
            Text(
                text = buildAnnotatedString {
                    extraItem.attributes.forEachIndexed { index, attribute ->
                        append(attribute)
                        if (index < extraItem.attributes.size - 1) {
                            // Append a new line if it's not the last item
                            append("\n")
                        }
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )}}
}
@Composable
fun KostGegevens (
    modifier: Modifier = Modifier,
    extraItemViewModel: ExtraItemViewModel,
    eventAddressViewModel: QuotationViewModel
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
            color = MainLighterColor,
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
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="Prijs",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="Subtotaal",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Basic formule",
                        textAlign = TextAlign.Left,
                        modifier= Modifier.width(90.dp),
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€350",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€350",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                extraItemViewModel.getListAddedItems().forEach(){
                    extraItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(35.dp)
                    ) {
                        Text(
                            text="${extraItem.title} x ${extraItem.amount}",
                            textAlign = TextAlign.Left,
                            modifier= Modifier.width(90.dp),
                            fontSize = 12.sp,
                            color = Color.Black,
                            overflow =TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text="€ ${extraItem.price}",
                            textAlign = TextAlign.Left,
                            modifier= Modifier,
                            fontSize = 12.sp,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.width(50.dp))
                        Text(
                            text="€ ${String.format("%.2f", extraItem.price * extraItem.amount)}",
                            textAlign = TextAlign.Left,
                            modifier= Modifier,
                            fontSize = 12.sp,
                            color = Color.Black,
                        )
                    }
                }
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Vervoerskosten",
                        textAlign = TextAlign.Left,
                        modifier= Modifier.width(90.dp),
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€ 0.75",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text="€ ${(eventAddressViewModel.getDistanceLong()?.div(1000)?.minus(20))?.times(
                            0.75
                        )}",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="TOTAAL EXCL BTW",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€ ${String.format("%.2f", extraItemViewModel.getTotalPrice() + 2.25 + 350)}", //TODO change to variables
                        textAlign = TextAlign.Left,
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
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€ ${String.format("%.2f", (extraItemViewModel.getTotalPrice() + 2.25 + 350) *0.21)}",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = Color.Gray
                )
                Row(
                    //horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(35.dp)
                ) {
                    Text(
                        text="Te betalen",
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text="€ ${String.format("%.2f", (extraItemViewModel.getTotalPrice() + 2.25 + 350) + (extraItemViewModel.getTotalPrice() + 2.25 + 350) *0.21)}",
                        textAlign = TextAlign.End,
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