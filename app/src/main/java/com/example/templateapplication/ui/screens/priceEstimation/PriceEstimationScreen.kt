package com.example.templateapplication.ui.screens.priceEstimation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationViewModel
import com.example.templateapplication.ui.commons.AddressTextField
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.DropDownSelect
import com.example.templateapplication.ui.commons.NumberOutlinedTextField
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.theme.md_theme_light_onSecondary
import com.example.templateapplication.ui.theme.md_theme_light_secondary
import com.example.templateapplication.ui.theme.md_theme_light_tertiary
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
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
            start = 2023,
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            onSelectDateRange = { startDate, endDate ->
                priceEstimationViewModel.updateDateRange(startDate, endDate)
            },
            showCalenderToggle = true,
        )
        SeperatingTitle(
            text = stringResource(id = R.string.guidedPrice_location_separator),
        )
        AddressTextField(
            showMap = false,
            placeResponse = priceEstimationUIState.placeResponse,
            getPredictionsFunction = { priceEstimationViewModel.getPredictions() },
            apiStatus = priceEstimationViewModel.googleMapsApiState,
            hasFoundPlace = { priceEstimationViewModel.placeFound() },
            updateInputFunction = { priceEstimationViewModel.updateInput(it) },
            googleMaps = priceEstimationUIState.googleMaps,
        )
        SeperatingTitle(
            text = stringResource(id = R.string.guidedPrice_details_separator),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            DropDownSelect(
                label =  stringResource(id = R.string.guidedPrice_formula_dropdown),
                isExpanded = priceEstimationUIState.formulaDropDownIsExpanded,
                setExpanded = { priceEstimationViewModel.setDropDownExpanded(it) },
                selectedOption = priceEstimationUIState.selectedFormula - 1,
                setSelectedOption = { priceEstimationViewModel.selectFormula(it) },
                dropDownOptions = priceEstimationUIState.dbData.formulas,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberOutlinedTextField(
                label = stringResource(id = R.string.guidedPrice_formula_numberOfPeople),
                value = priceEstimationUIState.amountOfPeople,
                onValueChange = { priceEstimationViewModel.setAmountOfPeople(it) },
                keyboardController = keyboardController
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        if (priceEstimationUIState.selectedFormula != 1) {
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text =  stringResource(id = R.string.guidedPrice_formula_tripleBeer), modifier = Modifier.padding(horizontal = 12.dp))
                Checkbox(
                    checked = priceEstimationUIState.wantsTripelBeer,
                    onCheckedChange = { priceEstimationViewModel.setWantsTripelBeer(!priceEstimationUIState.wantsTripelBeer) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = md_theme_light_secondary,
                        checkmarkColor = md_theme_light_onSecondary,
                        uncheckedColor = md_theme_light_tertiary,
                    ),
                )
            }
        } else {
            priceEstimationViewModel.setWantsTripelBeer(false)
        }
        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(id = R.string.guidedPrice_formula_extraMaterial), modifier = Modifier.padding(horizontal = 12.dp))
            Checkbox(
                checked = priceEstimationUIState.wantsExtras,
                onCheckedChange = { priceEstimationViewModel.setWantsExtras(!priceEstimationUIState.wantsExtras) },
                colors = CheckboxDefaults.colors(
                    checkedColor = md_theme_light_secondary,
                    checkmarkColor = md_theme_light_onSecondary,
                    uncheckedColor = md_theme_light_tertiary,
                ),
            )
        }
        if (priceEstimationUIState.wantsExtras) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 85.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    priceEstimationUIState.dbData.equipment.forEachIndexed { _, equipment ->
                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(
                                checked = priceEstimationViewModel.hasSelectedExtraItem(equipment),
                                onCheckedChange = {
                                    priceEstimationViewModel.extraItemsOnCheckedChange(
                                        equipment
                                    )
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = md_theme_light_secondary,
                                    checkmarkColor = md_theme_light_onSecondary,
                                    uncheckedColor = md_theme_light_tertiary,
                                ),
                            )
                            Text(
                                text = equipment.title,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.guidedPrice_estimatedPrice),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
        )
        Text(
            text = stringResource(id = R.string.guidedPrice_disclaimer),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}