package com.example.templateapplication.ui.screens.quotationRequest

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.quotationRequest.ExtraItemState
import com.example.templateapplication.model.quotationRequest.QuotationRequestState
import com.example.templateapplication.model.quotationRequest.QuotationUiState
import com.example.templateapplication.ui.commons.AlertPopUp
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLighterColor
import com.example.templateapplication.ui.theme.MainLightestColor


@Composable
fun SummaryScreen (
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateEventGegevens: ()->Unit,
    navigateContactGegevens:()->Unit,
    navigateExtras: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val requestState by quotationRequestViewModel.quotationRequestState.collectAsState()
    val uiState by quotationRequestViewModel.quotationUiState.collectAsState()
    var showConfirmationPop by remember { mutableStateOf(false) }


    Column (
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        HeadOfPage1()
        Navigation(
            navigateContactGegevens=navigateContactGegevens,
            navigateEventGegevens = navigateEventGegevens,
            navigateExtras = navigateExtras
        )
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 15.dp),
            thickness = 4.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(25.dp))
        EventDetails(
            uiState = uiState,
            dateRange = quotationRequestViewModel.getDateRange()
        )
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 15.dp),
            thickness = 4.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(25.dp))
        ContactGegevens(
            requestState = requestState,
        )
        Spacer(modifier = Modifier.height(30.dp))

        if(quotationRequestViewModel.getListAddedItems().isNotEmpty()){
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 15.dp),
                thickness = 4.dp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(30.dp))
            ExtraEquipment (quotationRequestViewModel = quotationRequestViewModel)
        }
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 15.dp),
            thickness = 4.dp,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(25.dp))
        KostGegevens(quotationRequestViewModel = quotationRequestViewModel)
        Spacer(modifier = Modifier.height(30.dp))
        Button (
            onClick = {
                showConfirmationPop = true
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = DisabledButtonColor,
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
        ) {
            Text (text= stringResource(id = R.string.summaryData_quoteRequest))
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
    if (showConfirmationPop) {
        AlertPopUp(
            onDismissRequest = { showConfirmationPop = false },
            dialogTitle = stringResource(id = R.string.alert_title),
            dialogText = stringResource(id = R.string.alert_text),
            confirmText = stringResource(id = R.string.alert_confirmation),
            dismissText = stringResource(id = R.string.alert_dismiss),
            onConfirmation = { quotationRequestViewModel.sendQuotationRequest() }
        )
    }
}

@Composable
fun HeadOfPage1( // TODO there are two of these in this package?
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text= stringResource(id = R.string.summaryData_overview),
            textAlign = TextAlign.Center,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        LinearProgressIndicator(
            progress = { 1.00f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MainColor,
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
            text= stringResource(id = R.string.summaryData_navigation_goto),
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
                contentDescription = stringResource(id = R.string.summaryData_navigation_circle),
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.summaryData_navigation_event)) ,
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
                contentDescription = stringResource(id = R.string.summaryData_navigation_circle),
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.summaryData_navigation_contact)),
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
                contentDescription = stringResource(id = R.string.summaryData_navigation_circle),
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.summaryData_navigation_extraMaterial)),
                onClick = {navigateExtras()},
                style = TextStyle(fontSize = 22.sp)
            )
        }

    }
}

@Composable
fun EventDetails(
    modifier: Modifier = Modifier,
    uiState: QuotationUiState,
    dateRange: String,
    ) {

    var show by rememberSaveable { mutableStateOf(true) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text= stringResource(id = R.string.summaryData_eventDetails_title),
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
                        text=dateRange,
                        fontSize = 16.sp)
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {
                    Text(text=stringResource(id = R.string.summaryData_eventDetails_location),fontSize = 18.sp)
                },
                supportingContent = {
                    Text(
                        text=uiState.googleMaps.eventAddressAutocompleteCandidates.candidates[0].formatted_address,
                        fontSize = 16.sp)
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
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
    requestState: QuotationRequestState,
) {
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
                text= stringResource(id = R.string.summaryData_contactDetails_title),
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
                headlineContent = {Text(text=stringResource(id = R.string.summaryData_contactDetails_fullName),fontSize = 18.sp)},
                supportingContent = {Text(text=requestState.customer.firstName+" "+requestState.customer.lastName,fontSize = 16.sp)},
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {Text(text=stringResource(id = R.string.contactDetails_email),fontSize = 18.sp)},
                supportingContent = {Text(text=requestState.customer.email,fontSize = 16.sp)},
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {Text(text=stringResource(id = R.string.contactDetails_phone_number),fontSize = 18.sp)},
                supportingContent = {Text(text=requestState.customer.phoneNumber,fontSize = 16.sp)},
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            ListItem(
                headlineContent = {Text(text=stringResource(id = R.string.summaryData_contactDetails_adress), fontSize = 18.sp)},
                supportingContent = {
                    Text(
                        text=requestState.customer.billingAddress.street+" "+requestState.customer.billingAddress.houseNumber+", "+requestState.customer.billingAddress.postalCode + " " + requestState.customer.billingAddress.city,
                        fontSize = 16.sp
                    )},
                colors = ListItemDefaults.colors(
                    containerColor = Color(0XFFD3B98B)
                ),
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            if(requestState.customer.vatNumber.isNotBlank()){
                Spacer(modifier = Modifier.height(20.dp))
                ListItem(
                    headlineContent = {Text(text=stringResource(id = R.string.contactDetails_vat_number), fontSize = 18.sp)},
                    supportingContent = {
                        Text(
                            text=requestState.customer.vatNumber,
                            fontSize = 16.sp
                        )},
                    colors = ListItemDefaults.colors(
                        containerColor = Color(0XFFD3B98B)
                    ),
                    modifier = Modifier.padding(horizontal = 30.dp)
                )
            }

        }


    }
}

@Composable
fun ExtraEquipment(
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
) {
    var show by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text(
                text= stringResource(id = R.string.extraItems_title),
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
            quotationRequestViewModel.getListAddedItems().forEach { extraItem ->
                ExtraItemCard(
                    extraItem = extraItem,
                    onAmountChanged = {equipment, amount ->
                        quotationRequestViewModel.changeExtraItemAmount(equipment, amount)},
                    onRemoveItem= { quotationRequestViewModel.removeItemFromCart(extraItem) },
                    modifier = Modifier.padding(8.dp),
                )

            }
        }

        }

}

@Composable
fun ExtraItemCard( // TODO duplicate code -> make a component out of this
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
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = extraItem.title,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            // Price
            Text(
                text = "€ ${extraItem.price} x ${extraItem.amount} stuks",
                style = MaterialTheme.typography.bodyMedium,

                )

        }

}}
@Composable
fun KostGegevens (
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel // TODO rework so VM isn't passed into this composable
) {

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text= stringResource(id = R.string.summaryData_priceDetails_title),
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
                        text= stringResource(id = R.string.summaryData_priceDetails_description),
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text= stringResource(id = R.string.summaryData_priceDetails_price),
                        textAlign = TextAlign.Left,
                        modifier= Modifier,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                    Text(
                        text=stringResource(id = R.string.summaryData_priceDetails_subtotal),
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
                quotationRequestViewModel.getListAddedItems().forEach{
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
                        text="€ ${(quotationRequestViewModel.getDistanceLong()?.div(1000)?.minus(20))?.times(
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
                        text="€ ${String.format("%.2f", quotationRequestViewModel.getTotalPrice() + 2.25 + 350)}", //TODO change to variables
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
                        text="€ ${String.format("%.2f", (quotationRequestViewModel.getTotalPrice() + 2.25 + 350) *0.21)}",
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
                        text="€ ${String.format("%.2f", (quotationRequestViewModel.getTotalPrice() + 2.25 + 350) + (quotationRequestViewModel.getTotalPrice() + 2.25 + 350) *0.21)}",
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