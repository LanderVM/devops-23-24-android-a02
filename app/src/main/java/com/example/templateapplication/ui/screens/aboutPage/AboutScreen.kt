package com.example.templateapplication.ui.screens.aboutPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.ImperialScript
import com.example.templateapplication.ui.theme.MainColor


@Composable
fun AboutScreen (
    modifier: Modifier = Modifier,
    navigateEmailScreen:()->Unit,
) {

    var openDialog1 by remember { mutableStateOf(false) }
    var openDialog2 by remember { mutableStateOf(false) }
    var emailAddress by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    if (openDialog1) {
        PopUp1(
            setOpenDialog = {value -> openDialog1 = value},
            openNextDialog = {openDialog2 = true},
            onEmailChange = {emailAddress = it},
            email = emailAddress,
        )
    }
    if (openDialog2) {
        PopUp2(
            setOpenDialog = {value -> openDialog2 = value},
            emailAdress = emailAddress,
        )
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Info(openPopUp = {openDialog1 = true})
        Spacer(modifier = Modifier.height(50.dp))
        Pictures()
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun Info (
    modifier: Modifier = Modifier,
    openPopUp:()->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.about_screen_title),
            fontFamily = ImperialScript,
            fontSize = 50.sp,
            color = Color.DarkGray,
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(text=stringResource(R.string.about_minimum_space),style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_space_dimensions), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text=stringResource(R.string.about_requirements),style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_on_site_person), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_power_outlet), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Text(text=stringResource(R.string.about_passage),style = MaterialTheme.typography.titleMedium, fontSize = 22.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_narrowest_point), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_minimum_width), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text=stringResource(R.string.about_minimum_width_value), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(35.dp))
        Button (
            onClick = openPopUp,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC8A86E),
                disabledContainerColor = Color(0xFFf8f1e4),
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
        ) {
            Text (text= stringResource(R.string.about_more_info),fontSize = 22.sp)
        }
    }
}
@Composable
fun PopUp1(
    modifier: Modifier = Modifier,
    setOpenDialog: (Boolean) -> Unit,
    openNextDialog:()->Unit,
    onEmailChange:(String)->Unit,
    email:String
) {
    Dialog(onDismissRequest = {setOpenDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0XFFf2e6ce),
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.about_popup1_title),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(15.dp))
                InputVeld(
                    label = stringResource(id =R.string.about_email_label), value = email, onChange = onEmailChange
                )
                Spacer(modifier = Modifier.height(15.dp))
                Button (
                    onClick = {
                        setOpenDialog(false)
                        openNextDialog()
                              },
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor,
                        disabledContainerColor = DisabledButtonColor,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                ) {
                    Text(text= stringResource(id = R.string.about_continue))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
@Composable
fun PopUp2(
    modifier: Modifier = Modifier,
    setOpenDialog: (Boolean) -> Unit,
    emailAdress:String
) {
    Dialog(onDismissRequest = {setOpenDialog(false) }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0XFFf2e6ce),
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.about_popup2_title),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = emailAdress,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button (
                    onClick = {setOpenDialog(false)},
                    modifier = Modifier
                        .width(100.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor,
                        disabledContainerColor = DisabledButtonColor,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                ) {
                    Text(text=stringResource(id = R.string.about_close))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(
    modifier: Modifier = Modifier,label:String,value: String,onChange:(String)->Unit,
) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(0XFFD3B98B)) },
        value = value,
        onValueChange =onChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 55.dp)
            .height(55.dp)
            .padding(horizontal = 30.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0XFFb49763),
            unfocusedBorderColor = Color(0XFFb49763),
        ),

    )
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
                contentDescription = stringResource(id = R.string.about_foodtruck_image_description),
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
                    contentDescription = stringResource(id = R.string.about_people_pouring_drinks_image_description),
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
                    contentDescription = stringResource(id = R.string.about_poured_glass_image_description),
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
                    contentDescription = stringResource(id = R.string.about_tap_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}