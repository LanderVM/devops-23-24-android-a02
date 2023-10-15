package com.example.templateapplication

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar
import java.util.Date

@Composable
fun DatumScreen (modifier: Modifier = Modifier) {

    var startDate = remember { mutableStateOf("") }
    var endDate = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            color = Color(android.graphics.Color.parseColor("#C8A86E")),
            progress = 0.25f,
            trackColor = Color(android.graphics.Color.parseColor("#efe5d4")),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text="evenement",
            color = Color(android.graphics.Color.parseColor("#C8A86E")),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .height(17.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        DatumPicker(startDate,endDate)
    }
}

@Composable
fun DatumPicker(beginDatum:MutableState<String>,eindDatum:MutableState<String>) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val startYear: Int
    val startMonth: Int
    val startDay: Int

    val endYear: Int
    val endMonth: Int
    val endDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    startYear = mCalendar.get(Calendar.YEAR)
    startMonth = mCalendar.get(Calendar.MONTH)
    startDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    endYear = mCalendar.get(Calendar.YEAR)
    endMonth = mCalendar.get(Calendar.MONTH)
    endDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val startDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            beginDatum.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, startYear, startMonth, startDay
    )

    val endDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            eindDatum.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, endYear, endMonth, endDay
    )

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Datum",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Geselecteerde start datum: ${beginDatum.value}", fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            startDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor("#C8A86E"))) ) {

            Text(text = "Open Date Picker", color = Color.White)
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Geselecteerde eind datum: ${eindDatum.value}", fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            endDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor("#C8A86E"))) ) {

            Text(text = "Open Date Picker", color = Color.White)
        }

    }
}
