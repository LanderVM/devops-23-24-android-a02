package com.example.templateapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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

    var startDatum = remember { mutableStateOf("") }
    var eindDatum = remember { mutableStateOf("") }
    var startUur = remember { mutableStateOf("") }
    var eindUur = remember { mutableStateOf("") }

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
        Text(
            text = "Datum",
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Spacer(modifier= Modifier.height(40.dp))
        DatumPicker(datum = startDatum, naamDatum = "start datum")
        Spacer(modifier = Modifier.height(10.dp))
        TijdPicker(label = "begin uur", value = startUur.value, onChange ={startUur.value=it} )
        Spacer(modifier= Modifier.height(50.dp))
        DatumPicker(datum = eindDatum, naamDatum = "eind datum")
        Spacer(modifier = Modifier.height(10.dp))
        TijdPicker(label = "eind uur", value = eindUur.value, onChange ={eindUur.value=it} )
        Spacer(modifier= Modifier.height(30.dp))
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
