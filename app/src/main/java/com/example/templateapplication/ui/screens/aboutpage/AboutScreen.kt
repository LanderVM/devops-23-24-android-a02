package com.example.templateapplication.ui.screens.aboutpage


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import androidx.compose.ui.graphics.Color

@Composable
fun AboutScreen (
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Info()
    }
}

@Composable
fun Info (
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text="Minimaal te voorziene ruimte:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Text(text="5m x 2m x 2m", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text="Benodigtheden:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Text(text="Iemand ter plaatse", fontSize = 18.sp)
        Text(text="Stopcontact", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text="Doorgang:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Text(text="Smalste punt op de route naar", fontSize = 18.sp)
        Text(text="plaats van opstelling dinet minimaal", fontSize = 18.sp)
        Text(text="3m te zijn!", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(35.dp))
        Button (
            onClick = {},
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.main))),
                disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.disabledButtonColor))),
                contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit))),
                disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.wit)))
            ),
        ) {
            Text (text= "Meer info",fontSize = 22.sp)
        }
    }
}