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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.md_theme_light_onPrimary
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(
    modifier: Modifier = Modifier,
    state: DateRangePickerState,
    onSelectDateRange: (Long?, Long?) -> Unit,
    showCalenderToggle: Boolean,
    enableRecheckFunction: () -> Unit = {},
) {
    SeperatingTitle(
        text = "Datum",
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LaunchedEffect(state.selectedStartDateMillis) {
            onSelectDateRange(state.selectedStartDateMillis, state.selectedEndDateMillis)
            enableRecheckFunction()
        }

        LaunchedEffect(state.selectedEndDateMillis) {
            onSelectDateRange(state.selectedStartDateMillis, state.selectedEndDateMillis)
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
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFFe9dcc5),
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color(0xFFC8A86E),
                dayInSelectionRangeContainerColor = Color(0xFFe9dcc5),
                dayInSelectionRangeContentColor = Color.White,
                selectedDayContainerColor =  Color(0xFFC8A86E),
                selectedDayContentColor = md_theme_light_onPrimary,
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

private fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
    return dateFormat.format(calender.timeInMillis)
}