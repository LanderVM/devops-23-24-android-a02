package com.example.templateapplication.ui.screens.quotationRequest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.ui.commons.ClearableOutlinedTextField
import com.example.templateapplication.ui.commons.ValidationTextFieldApp
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.utils.ReplyNavigationType

@Composable
fun PersonalDetailsScreen(
    navigationType: ReplyNavigationType,
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateExtras: () -> Unit,
) {
    val requestState by quotationRequestViewModel.quotationRequestState.collectAsState()

    val scrollState = rememberScrollState()

    Column {
        ProgressieBar(
            text = stringResource(id = R.string.contactDetails_personal_info),
            progression = 0.50f,
        )
        SeperatingTitle(
            text = stringResource(id = R.string.contactDetails_contact_info),
        )
        PersonalDetailsForm(
            navigationType =navigationType,
            navigateExtras = navigateExtras,
            quotationRequestViewModel = quotationRequestViewModel,

            formState = quotationRequestViewModel.formState,
            onEvent = { quotationRequestViewModel.onEvent(it) },
        )
        Spacer(modifier = Modifier.height(30.dp))

    }
}

@Composable
fun PersonalDetailsForm (
    navigationType: ReplyNavigationType,
    navigateExtras: () -> Unit,
    quotationRequestViewModel : QuotationRequestViewModel,
    modifier: Modifier = Modifier,
    formState: MainState,
    onEvent: (MainEvent) -> Unit,
) {

    var columnAmount : Int
    val padding : Dp
    val spacer : Dp
    when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            columnAmount = 2
            padding = 50.dp; 30.dp
            spacer = 30.dp
        }
        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            columnAmount = 4
            padding = 60.dp
            spacer = 60.dp
        }
        else -> {
            columnAmount = 1
            padding = 60.dp
            spacer = 30.dp
        }
    }
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = padding),
        columns = GridCells.Fixed(columnAmount),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item{
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_first_name),
                text = formState.firstName,
                onValueChange = {newValue -> onEvent(MainEvent.FirstNameChanged(newValue))},
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.firstNameError != null,
                errorMessage = formState.firstNameError,
            )
        }
        item{
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_name),
                text = formState.lastName,
                onValueChange = {newValue -> onEvent(MainEvent.LastNameChanged(newValue))},
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.lastNameError != null,
                errorMessage = formState.lastNameError,
            )
        }
        item{
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.strPhoneNumber),
                text = formState.phoneNumber,
                onValueChange = {newValue -> onEvent(MainEvent.PhoneNumberChanged(newValue))},
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.phoneNumberError != null,
                errorMessage = formState.phoneNumberError,
            )
        }
        item{
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.strEmail),
                text = formState.email,
                onValueChange = { newValue -> onEvent(MainEvent.EmailChanged(newValue))},
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.emailError != null,
                errorMessage = formState.emailError,
            )
        }
        item(span = { GridItemSpan(maxLineSpan)}){
            Spacer(modifier = Modifier.size(30.dp))
        }
        item{
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_street),
                text = formState.street,
                onValueChange = { newValue -> onEvent(MainEvent.StreetChanged(newValue))},
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.streetError != null,
                errorMessage = formState.streetError,
            )
        }
        item {
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_house_number),
                text = formState.houseNumber,
                onValueChange = { newValue -> onEvent(MainEvent.HouseNumberChanged(newValue))},
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.houseNumberError != null,
                errorMessage = formState.houseNumberError,
            )
        }
        item {
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_city),
                text = formState.city,
                onValueChange = { newValue -> onEvent(MainEvent.CityChanged(newValue))},
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.cityError != null,
                errorMessage = formState.cityError,
            )
        }
        item {
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_postal_code),
                text = formState.postalCode,
                onValueChange = { newValue -> onEvent(MainEvent.PostalCodeChanged(newValue))},
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
                singleLine = true,
                isError = formState.postalCodeError != null,
                errorMessage = formState.postalCodeError,
            )
        }
        item(){
            ValidationTextFieldApp(
                placeholder = stringResource(id = R.string.contactDetails_vat_number),
                text = formState.vat,
                onValueChange = { newValue -> onEvent(MainEvent.VatChanged(newValue))},
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                singleLine = true,
                isError = formState.vatError != null,
                errorMessage = formState.vatError,
            )
        }
        item(span = { GridItemSpan(maxLineSpan)}){
            Spacer(modifier = Modifier.size(spacer))
        }
        item(span = { GridItemSpan(maxLineSpan)}) {
            NextPageButton(
                navigeer = navigateExtras,
                enabled = quotationRequestViewModel.quotationScreenCanNavigate(),
            )
        }

    }
}

@Composable
fun FacturationForm(
    modifier: Modifier = Modifier,
    onEvent: (MainEvent) -> Unit,
    formState: MainState,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
}
