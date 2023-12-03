package com.example.templateapplication.ui.screens.contactgegevenspage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.ui.commons.ClearableOutlinedTextField
import com.example.templateapplication.ui.commons.CustomTextFieldApp
import com.example.templateapplication.ui.commons.NextPageButton
import com.example.templateapplication.ui.commons.ProgressieBar
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.screens.MainEvent
import com.example.templateapplication.ui.screens.QuotationRequestViewModel

@Composable
fun PersonalDetailsScreen(
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel = viewModel(),
    navigateExtras: () -> Unit,
) {
    val requestState by quotationRequestViewModel.quotationRequestState.collectAsState()

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
        PersonalDetailsForm(
            firstName = requestState.customer.firstName,
            onFirstNameChange = { quotationRequestViewModel.setFirstName(it) },
            lastName = requestState.customer.lastName,
            onLastNameChange = { quotationRequestViewModel.setLastName(it) },
            phoneNumber = requestState.customer.phoneNumber,
            onPhoneNumberChange = { quotationRequestViewModel.setPhoneNumber(it) },
            email = requestState.customer.email,
            onEmailChange = { quotationRequestViewModel.setEmail(it) },
        )
        Spacer(modifier = Modifier.height(30.dp))
        FacturationForm(
            quotationRequestViewModel= quotationRequestViewModel,
            streetState = requestState.customer.billingAddress.street,
            onStretChange = { quotationRequestViewModel.setStreet(it) },
            houseNumberState = requestState.customer.billingAddress.houseNumber,
            onHouseNumberChange = { quotationRequestViewModel.setHouseNumber(it) },
            cityState = requestState.customer.billingAddress.city,
            onCityChange = { quotationRequestViewModel.setCity(it) },
            postalCode = requestState.customer.billingAddress.postalCode,
            onPostalCodeChange = { quotationRequestViewModel.setPostalCode(it) },
            vatNumberState = requestState.customer.vatNumber,
            onVatNumberChange = { quotationRequestViewModel.setVatNumber(it) },
        )
        Spacer(modifier = Modifier.height(30.dp))
        NextPageButton(
            navigeer = navigateExtras,
            enabled = quotationRequestViewModel.canNavigateNext(),
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun PersonalDetailsForm (
    modifier: Modifier = Modifier,
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SeperatingTitle(
            text = stringResource(id = R.string.contactDetails_contact_info),
        )
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_first_name),
            value = firstName,
            onValueChange = onFirstNameChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_name),
            value = lastName,
            onValueChange = onLastNameChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_phone_number),
            value = phoneNumber,
            onValueChange = onPhoneNumberChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_email),
            value = email,
            onValueChange = onEmailChange
        )
    }
}

@Composable
fun FacturationForm(
    modifier: Modifier = Modifier,
    quotationRequestViewModel: QuotationRequestViewModel,
    streetState: String,
    onStretChange: (String) -> Unit,
    houseNumberState: String,
    onHouseNumberChange: (String) -> Unit,
    cityState: String,
    onCityChange: (String) -> Unit,
    postalCode: String,
    onPostalCodeChange: (String) -> Unit,
    vatNumberState: String,
    onVatNumberChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SeperatingTitle(
            text = stringResource(id = R.string.contactDetails_address_info),
        )
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_street),
            value = streetState,
            onValueChange = onStretChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_house_number),
            value = houseNumberState,
            onValueChange = onHouseNumberChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_city),
            value = cityState,
            onValueChange = onCityChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_postal_code),
            value = postalCode,
            onValueChange = onPostalCodeChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        ClearableOutlinedTextField(
            label = stringResource(id = R.string.contactDetails_vat_number),
            value = vatNumberState,
            onValueChange = onVatNumberChange
        )
        CustomTextFieldApp(
            placeholder = stringResource(id = R.string.strEmail),
            text = quotationRequestViewModel.formState.email,
            onValueChange = {
                quotationRequestViewModel.onEvent(MainEvent.EmailChanged(it))
            },
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            singleLine = true,
            isError = quotationRequestViewModel.formState.emailError != null,
            errorMessage = quotationRequestViewModel.formState.emailError,
        )
    }
}
