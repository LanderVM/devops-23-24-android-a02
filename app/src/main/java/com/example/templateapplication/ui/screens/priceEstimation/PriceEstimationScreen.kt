package com.example.templateapplication.ui.screens.priceEstimation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationDetailsApiState
import com.example.templateapplication.ui.commons.AddressTextField
import com.example.templateapplication.ui.commons.CustomDateRangePicker
import com.example.templateapplication.ui.commons.DropDownSelect
import com.example.templateapplication.ui.commons.NumberOutlinedTextField
import com.example.templateapplication.ui.commons.SeparatingTitle
import com.example.templateapplication.ui.screens.priceEstimation.components.EquipmentCheckList
import com.example.templateapplication.ui.screens.priceEstimation.components.EstimatedPriceText
import com.example.templateapplication.ui.screens.priceEstimation.components.WantsEquipmentCheckbox
import com.example.templateapplication.ui.screens.priceEstimation.components.WantsTripelBeerCheckbox
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.utils.ReplyNavigationType
import java.time.Instant
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    navigationType: ReplyNavigationType,
    priceEstimationViewModel: PriceEstimationViewModel = viewModel(factory = PriceEstimationViewModel.Factory),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    val scrollState = rememberScrollState()
    val priceEstimationUIState by priceEstimationViewModel.estimationDetailsState.collectAsState()

    val selectedStartDate by remember { mutableStateOf(priceEstimationUIState.startDate) }
    val selectedEndDate by remember { mutableStateOf(priceEstimationUIState.endDate) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate?.timeInMillis,
        yearRange = IntRange(
            start = Calendar.getInstance().get(Calendar.YEAR),
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 2
        ),
        selectableDates = object : SelectableDates {

            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val isTimeInPast = utcTimeMillis < System.currentTimeMillis()
                if (isTimeInPast) return false

                for (item in priceEstimationUIState.dbData.unavailableDates)
                    if (Instant.ofEpochMilli(utcTimeMillis) in item.startTime..item.endTime)
                        return false
                return true
            }
        })
    when (val detailsApiState = priceEstimationViewModel.retrieveUiDetailsApiState) {
        is PriceEstimationDetailsApiState.Error -> Text(text = detailsApiState.errorMessage)
        PriceEstimationDetailsApiState.Loading -> Text(text = stringResource(id = R.string.loading))
        PriceEstimationDetailsApiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomDateRangePicker(
                    navigationType = navigationType,
                    state = dateRangePickerState,
                    onSelectDateRange = { startDate, endDate ->
                        priceEstimationViewModel.updateDateRange(startDate, endDate)
                    },
                    showCalenderToggle = true,
                )
                SeparatingTitle(
                    text = stringResource(id = R.string.guidePrice_location_separator),
                )
                AddressTextField(
                    navigationType = navigationType,
                    showMap = false,
                    placeResponse = priceEstimationUIState.placeResponse,
                    getPredictionsFunction = { priceEstimationViewModel.getPredictions() },
                    apiStatus = priceEstimationViewModel.googleMapsApiState,
                    hasFoundPlace = { priceEstimationViewModel.placeFound() },
                    onValueChange = { priceEstimationViewModel.updateInput(it) },
                    googleMaps = priceEstimationUIState.googleMaps,
                )
                SeparatingTitle(
                    text = stringResource(id = R.string.guidePrice_details_separator),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                ) {
                    DropDownSelect(
                        label = stringResource(id = R.string.guidePrice_formula_dropdown),
                        isExpanded = priceEstimationUIState.formulaDropDownIsExpanded,
                        setExpanded = { priceEstimationViewModel.setDropDownExpanded(it) },
                        selectedOption = priceEstimationUIState.selectedFormula - 1,
                        setSelectedOption = { priceEstimationViewModel.selectFormula(it) },
                        dropDownOptions = priceEstimationUIState.dbData.formulas,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    NumberOutlinedTextField(
                        label = stringResource(id = R.string.guidePrice_formula_numberOfPeople),
                        value = priceEstimationUIState.amountOfPeople,
                        onValueChange = { priceEstimationViewModel.setAmountOfPeople(it) },
                        keyboardController = keyboardController
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                WantsTripelBeerCheckbox(
                    selectedFormula = priceEstimationUIState.selectedFormula,
                    hasCheck = priceEstimationUIState.wantsTripelBeer,
                    onCheck = { priceEstimationViewModel.setWantsTripelBeer(it) }
                )
                WantsEquipmentCheckbox(
                    hasCheck = priceEstimationUIState.wantsExtras,
                    onCheck = { priceEstimationViewModel.setWantsExtras(it) }
                )
                if (priceEstimationUIState.wantsExtras)
                    EquipmentCheckList(
                        list = priceEstimationUIState.dbData.equipment,
                        hasChecked = { priceEstimationViewModel.hasSelectedExtraItem(it) },
                        onCheck = { priceEstimationViewModel.extraItemsOnCheckedChange(it) }
                    )
                Spacer(modifier = Modifier.height(24.dp))
                EstimatedPriceText(calculatePriceApiState = priceEstimationViewModel.calculatePriceApiState)
                Text(
                    text = stringResource(id = R.string.guidePrice_disclaimer),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                )
                Button(
                    onClick = {
                        priceEstimationViewModel.getPriceEstimation()
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor,
                        disabledContainerColor = DisabledButtonColor,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                ) {
                    Text(text = stringResource(id = R.string.guidePrice_calculatePrice))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}