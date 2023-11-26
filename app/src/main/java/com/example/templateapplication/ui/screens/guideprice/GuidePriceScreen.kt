package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.EventAddressViewModel
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.model.guidePriceEstimation.PriceEstimationViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DateRangePicker
import com.example.templateapplication.ui.commons.Titel
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

    val estimationDetailsState by priceEstimationViewModel.estimationDetailsState.collectAsState()

    val formulasList = estimationDetailsState.formulas
    val materialsList = listOf(
        "Barkoeler 320L",
        "Lichtslinger",
        "Diepvries 80L",
        "Soepketel",
        "Biertafelset",
        "Schapenvacht"
    )

    var formulaDropDownExpand by remember { mutableStateOf(false) }
    var selectedFormula by remember { mutableIntStateOf(0) }
    var amountOfPersons by remember { mutableStateOf("") }


    var wantsTripelBier by remember { mutableStateOf(false) }
    var wantsExtras by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<String>() }

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
        Titel(
            text = "Locatie",
        )
        AutoCompleteComponent(
            eventAddressViewModel = eventAddressViewModel,
            showMap = false,
            enableRecheckFunction = {}
        )
        Titel(
            text = "Details",
        )
        /*        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
        ) {
            ExposedDropdownMenuBox(
                expanded = formulaDropDownExpand,
                onExpandedChange = { formulaDropDownExpand = !formulaDropDownExpand },
            ) {
                formulasList?.get(selectedFormula)?.let {
                    OutlinedTextField(
                        readOnly = true,
                        value = it.title,
                        onValueChange = { },
                        label = {
                            Text(
                                text = "Formule",
                                color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = formulaDropDownExpand
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.5f),
                    )
                }
                ExposedDropdownMenu(
                    expanded = formulaDropDownExpand,
                    onDismissRequest = {
                        formulaDropDownExpand = false
                    }
                ) {
                    formulasList?.forEachIndexed { index, s ->
                        DropdownMenuItem(text = { Text(s.title) }, onClick = {
                            selectedFormula = index
                            formulaDropDownExpand = !formulaDropDownExpand
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = amountOfPersons,
                onValueChange = {
                    if (it.isDigitsOnly()) {
                        amountOfPersons = it
                    }
                },
                label = {
                    Text(
                        text = "Aantal Pers.",
                        color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                modifier = Modifier
                    .clickable { keyboardController?.show() },
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        if (selectedFormula != 0) {
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Ik wil tripel bier", modifier = Modifier.padding(horizontal = 12.dp))
                Checkbox(
                    checked = wantsTripelBier,
                    onCheckedChange = { wantsTripelBier = !wantsTripelBier },
                    colors = CheckboxDefaults.colors(
                        checkedColor = secondary,
                        checkmarkColor = onSecondary,
                        uncheckedColor = tertiary,
                    ),
                )
            }
        }
        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Extra materiaal nodig", modifier = Modifier.padding(horizontal = 12.dp))
            Checkbox(
                checked = wantsExtras,
                onCheckedChange = { wantsExtras = !wantsExtras },
                colors = CheckboxDefaults.colors(
                    checkedColor = secondary,
                    checkmarkColor = onSecondary,
                    uncheckedColor = tertiary,
                ),
            )
        }
        if (wantsExtras) {
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
                    materialsList.forEachIndexed { index, s ->
                        Row(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Checkbox(
                                checked = selectedItems.contains(s),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        selectedItems.add(s)
                                    } else {
                                        selectedItems.remove(s)
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = secondary,
                                    checkmarkColor = onSecondary,
                                    uncheckedColor = tertiary,
                                ),
                            )
                            Text(
                                text = s,
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
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
        )
        Text(
            text = "Disclaimer: deze prijs is een schatting en ligt dus niet vast.",
            modifier = Modifier.padding(horizontal = 12.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
        Spacer(modifier = Modifier.height(24.dp))*/
    }
}