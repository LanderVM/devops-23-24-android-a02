package com.example.templateapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.stream.LongStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvenementScreen (modifier: Modifier = Modifier) {

    val datumState = rememberDateRangePickerState()
    val beginTijdState = rememberTimePickerState()
    val eindTijdState = rememberTimePickerState()

    var invalidDates:LongArray = longArrayOf()

    val validatorFunction:(Long)->Boolean = {datum:Long-> !LongStream.of(*invalidDates).anyMatch{n->n==datum}}

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
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
        DatumPart(state = datumState, validatorFunction = validatorFunction)
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = beginTijdState, welkeTijd = "Begin tijd")
        Spacer(modifier = Modifier.height(20.dp))
        TimePart(state = eindTijdState, welkeTijd = "Eind tijd")
        /*
        Spacer(modifier= Modifier.height(40.dp))
        DatumPicker(datum = startDatum, naamDatum = "start datum")
        Spacer(modifier = Modifier.height(10.dp))
        TijdPicker(label = "begin uur", value = startUur.value, onChange ={startUur.value=it} )
        Spacer(modifier= Modifier.height(50.dp))
        DatumPicker(datum = eindDatum, naamDatum = "eind datum")
        Spacer(modifier = Modifier.height(10.dp))
        TijdPicker(label = "eind uur", value = eindUur.value, onChange ={eindUur.value=it} )
        Spacer(modifier= Modifier.height(30.dp))
        */
    }
}
fun getFormattedDate(timeInMillis: Long): String{
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatumPart (
    modifier: Modifier = Modifier,
    state: DateRangePickerState,
    validatorFunction:(Long)->Boolean
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Datum",
            textAlign = TextAlign.Center,
            fontSize = 40.sp
        )
        DateRangePicker(
            state,
            modifier = Modifier.height(450.dp),
            dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
            dateValidator = validatorFunction,
            title = {
                Text(text = "Selecteer begin dag tot eind dag evenement", modifier = Modifier
                    .padding(16.dp))
            },
            headline = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(Modifier.weight(1f)) {
                        (if(state.selectedStartDateMillis!=null) state.selectedStartDateMillis?.let { getFormattedDate(it) } else "Start datum")?.let { Text(text = it) }
                    }
                    Box(Modifier.weight(1f)) {
                        (if(state.selectedEndDateMillis!=null) state.selectedEndDateMillis?.let { getFormattedDate(it) } else "Eind datum")?.let { Text(text = it) }
                    }
                }
            },
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = Color.Blue,
                titleContentColor = Color.Black,
                headlineContentColor = Color.Black,
                weekdayContentColor = Color.Black,
                subheadContentColor = Color.Black,
                yearContentColor = Color.Green,
                currentYearContentColor = Color.Red,
                selectedYearContainerColor = Color.Red,
                disabledDayContentColor = Color.Gray,
                todayDateBorderColor = Color.Blue,
                dayInSelectionRangeContainerColor = Color.LightGray,
                dayInSelectionRangeContentColor = Color.White,
                selectedDayContainerColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePart (
    modifier: Modifier = Modifier,
    state:TimePickerState,
    welkeTijd:String
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text=welkeTijd,
            textAlign = TextAlign.Center,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        TimeInput(state = state)
    }
}

@Composable
fun DatumPicker (datum:MutableState<String>,naamDatum:String) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            datum.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Geselecteerde ${naamDatum}: ${datum.value}", fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(android.graphics.Color.parseColor("#C8A86E"))) ) {

            Text(text = "Open Datum Picker", color = Color.White)
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TijdPicker (label:String,value:String,onChange:(String)->Unit) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor("#C8A86E"))) },
        value = value,
        onValueChange =onChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor("#C8A86E")),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor("#C8A86E"))
        ),
        //trailingIcon = Icon(Icons.Default.Clear,contentDescription = null),
    )


}
