package com.example.templateapplication.ui.screens.guideprice

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.model.adres.EventAddressViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationViewModel
import com.example.templateapplication.ui.commons.AddressTextField
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.DropDownSelect
import com.example.templateapplication.ui.commons.NumberOutlinedTextField
import com.example.templateapplication.ui.commons.SeperatingTitle
import com.example.templateapplication.ui.theme.onSecondary
import com.example.templateapplication.ui.theme.secondary
import com.example.templateapplication.ui.theme.tertiary
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
    priceEstimationViewModel: PriceEstimationViewModel = viewModel(factory = PriceEstimationViewModel.Factory),
    formulaViewModel: FormulaViewModel = viewModel(),
    eventAddressViewModel: EventAddressViewModel = viewModel(),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    val scrollState = rememberScrollState()
    val formulaUIState by formulaViewModel.formulaUiState.collectAsState()
    val priceEstimationUIState by priceEstimationViewModel.estimationDetailsState.collectAsState()

    val selectedStartDate by remember { mutableStateOf(formulaUIState.startDate) }
    val selectedEndDate by remember { mutableStateOf(formulaUIState.endDate) }
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
            formulaViewModel = formulaViewModel,
            showCalenderToggle = true,
        )
        SeperatingTitle(
            text = "Locatie",
        )
        AddressTextField(
            eventAddressViewModel = eventAddressViewModel,
            showMap = false,
            enableRecheckFunction = {}
        )
        SeperatingTitle(
            text = "Details",
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            DropDownSelect(
                label = "Formule",
                isExpanded = priceEstimationUIState.formulaDropDownIsExpanded,
                setExpanded = { priceEstimationViewModel.setDropDownExpanded(it) },
                selectedOption = priceEstimationUIState.selectedFormula - 1,
                setSelectedOption = { priceEstimationViewModel.selectFormula(it) },
                dropDownOptions = priceEstimationUIState.dbData.formulas,
            )
            Spacer(modifier = Modifier.width(8.dp))
            NumberOutlinedTextField(
                label = "Aantal Pers.",
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
                Text(text = "Ik wil tripel bier", modifier = Modifier.padding(horizontal = 12.dp))
                Checkbox(
                    checked = priceEstimationUIState.wantsTripelBeer,
                    onCheckedChange = { priceEstimationViewModel.setWantsTripelBeer(!priceEstimationUIState.wantsTripelBeer) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = secondary,
                        checkmarkColor = onSecondary,
                        uncheckedColor = tertiary,
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
            Text(text = "Extra materiaal nodig", modifier = Modifier.padding(horizontal = 12.dp))
            Checkbox(
                checked = priceEstimationUIState.wantsExtras,
                onCheckedChange = { priceEstimationViewModel.setWantsExtras(!priceEstimationUIState.wantsExtras) },
                colors = CheckboxDefaults.colors(
                    checkedColor = secondary,
                    checkmarkColor = onSecondary,
                    uncheckedColor = tertiary,
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
                                    checkedColor = secondary,
                                    checkmarkColor = onSecondary,
                                    uncheckedColor = tertiary,
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
            text = "Dit zal tussen de 350 en 500 euro liggen.",
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
        )
        Text(
            text = "Disclaimer: deze prijs is een schatting en ligt dus niet vast.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}