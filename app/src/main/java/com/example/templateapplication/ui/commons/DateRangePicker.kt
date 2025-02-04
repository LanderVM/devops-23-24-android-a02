package com.example.templateapplication.ui.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.md_theme_light_onPrimary
import com.example.templateapplication.ui.utils.ReplyNavigationType
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Composable function for displaying a custom date range picker.
 *
 * This function renders a date range picker that allows users to select a start and end date.
 * It supports various navigation types and can trigger actions upon date selection.
 *
 * @param navigationType The type of navigation being used in the UI, affecting the width of the picker.
 * @param state The state of the date range picker, containing selected dates.
 * @param onSelectDateRange Callback function to be executed when a date range is selected.
 * @param showCalenderToggle Boolean flag to show or hide the calendar mode toggle.
 * @param enableRecheckFunction Optional function to be executed when the date range changes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePicker(
    navigationType: ReplyNavigationType,
    state: DateRangePickerState,
    onSelectDateRange: (Long?, Long?) -> Unit,
    showCalenderToggle: Boolean,
    enableRecheckFunction: () -> Unit = {},
) {
    SeparatingTitle(
        text = stringResource(id = R.string.dateRangePicker_separator),
    )

    val dateWidth: Dp = when (navigationType) {
        ReplyNavigationType.NAVIGATION_RAIL -> {
            500.dp
        }

        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            700.dp
        }

        else -> {
            400.dp
        }
    }
    Column(
        modifier = Modifier
            .width(dateWidth)
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
                    text = stringResource(id = R.string.dateRangePicker_info),
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
                        } else stringResource(id = R.string.dateRangePicker_startDate))?.let {
                            Text(
                                text = it
                            )
                        }
                    }
                    Box(Modifier.weight(1f)) {
                        (if (state.selectedEndDateMillis != null) state.selectedEndDateMillis?.let {
                            getFormattedDate(
                                it
                            )
                        } else stringResource(id = R.string.dateRangePicker_endDate))?.let {
                            Text(
                                text = it
                            )
                        }
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
                selectedDayContainerColor = Color(0xFFC8A86E),
                selectedDayContentColor = md_theme_light_onPrimary,
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Formats the given time in milliseconds to a string in "dd/MM/yyyy" format.
 *
 * @param timeInMillis Time in milliseconds since Unix epoch to be formatted.
 * @return A string representing the formatted date.
 */
private fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH)
    return dateFormat.format(calender.timeInMillis)
}