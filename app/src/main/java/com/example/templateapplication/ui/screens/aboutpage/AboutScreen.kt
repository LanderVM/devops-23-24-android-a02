package com.example.templateapplication.ui.screens.aboutpage


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.templateapplication.ui.theme.ImperialScript

@Composable
fun AboutScreen (
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Info()
        Spacer(modifier = Modifier.height(50.dp))
        Pictures()
        Spacer(modifier = Modifier.height(40.dp))
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
        Text(
            text = "Over de foodtruck",
            fontFamily = ImperialScript,
            fontSize = 50.sp,
            color = Color.DarkGray,
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(text="Minimaal te voorziene ruimte:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="5m x 2m x 2m", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text="Benodigtheden:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="Iemand ter plaatse", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="Stopcontact", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text="Doorgang:",style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="Smalste punt op de route naar", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text="plaats van opstelling dient minimaal", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
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

@Composable
fun Pictures () {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.foto3),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box (
                modifier = Modifier
                    .fillMaxHeight()
                    .width(115.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.foto4),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxHeight()
                    .width(115.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.foto7),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxHeight()
                    .width(115.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.foto8),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}