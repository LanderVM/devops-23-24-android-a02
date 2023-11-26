package com.example.templateapplication.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.model.formules.FormulaViewModel
import com.example.templateapplication.ui.theme.onPrimary
import java.text.SimpleDateFormat
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    state: DateRangePickerState,
    formulaViewModel: FormulaViewModel,
    showCalenderToggle: Boolean,
) {
    Titel(
        text = "Datum",
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LaunchedEffect(state.selectedStartDateMillis) {
            formulaViewModel.updateDateRange(state.selectedStartDateMillis, state.selectedEndDateMillis)
        }

        DateRangePicker(
            state,
            modifier = Modifier.height(450.dp),
            dateFormatter = DatePickerDefaults.dateFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            title = {
                Text(
                    text = "Selecteer begin dag tot eind dag evenement",
                    modifier = Modifier
                        .padding(16.dp),
                )
            },
            headline = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(Modifier.weight(1f)) {
                        (if (state.selectedStartDateMillis != null) state.selectedStartDateMillis?.let {
                            getFormattedDate(
                                it
                            )
                        } else "Start datum")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                            getFormattedDate(
                                it
                            )
                        } else "Eind datum")?.let { Text(text = it) }
                    }
                }
            },
            showModeToggle = showCalenderToggle,
            // TODO color cleanup
            colors = DatePickerDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.lichter))),
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
                dayInSelectionRangeContainerColor = Color(
                    android.graphics.Color.parseColor(
                        stringResource(R.string.lichterder)
                    )
                ),
                dayInSelectionRangeContentColor = Color.White,
                selectedDayContainerColor = Color(android.graphics.Color.parseColor(stringResource(R.string.main))),
                selectedDayContentColor = onPrimary,
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}