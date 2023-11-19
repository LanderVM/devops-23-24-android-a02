package com.example.templateapplication.ui.screens.guideprice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.templateapplication.R
import com.example.templateapplication.model.adres.EventAdresViewModel
import com.example.templateapplication.model.formules.FormuleViewModel
import com.example.templateapplication.ui.commons.AutoCompleteComponent
import com.example.templateapplication.ui.commons.DatumPart
import com.example.templateapplication.ui.commons.Titel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidePriceScreen(
    modifier: Modifier = Modifier,
    formulaViewModel: FormuleViewModel = viewModel(),
    eventAdresViewModel: EventAdresViewModel = viewModel()
) {
    val formulasList = listOf("Basic", "All in", "Extended")
    val formulaUIState by formulaViewModel.formuleUiState.collectAsState()
    val selectedStartDate = remember { mutableStateOf(formulaUIState.beginDatum) }
    val selectedEndDate = remember { mutableStateOf(formulaUIState.eindDatum) }
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableIntStateOf(0) }

    val dataState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = selectedStartDate.value?.timeInMillis,
        initialSelectedEndDateMillis = selectedEndDate.value?.timeInMillis,
        yearRange = IntRange(
            start = 2023,
            endInclusive = Calendar.getInstance().get(Calendar.YEAR) + 1
        ),
    )

    var wantsExtras by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DatumPart(
            state = dataState,
            formulaViewModel = formulaViewModel,
            showCalenderToggle = true,
        )
        Titel(
            text = "Locatie",
        )
        AutoCompleteComponent(eventAddressViewModel = eventAdresViewModel, showMap = false)
        Titel(
            text = "Details",
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                readOnly = true,
                value = formulasList[selectedOption],
                onValueChange = { },
                label = { Text("Formule") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier.menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                formulasList.forEachIndexed { index, s ->
                    DropdownMenuItem(text = { Text(s) }, onClick = {
                        selectedOption = index
                        expanded = !expanded
                    })
                }
            }
        }
        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Extra materiaal nodig", modifier = Modifier.padding(horizontal = 12.dp))
            Checkbox(
                checked = wantsExtras,
                onCheckedChange = { wantsExtras = !wantsExtras},
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    uncheckedColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    checkmarkColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                ),
            )
        }
        if (wantsExtras) {
            Text(text = "user wants extras")
        }
    }
}
