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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.utils.ReplyNavigationType


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    navigationType: ReplyNavigationType,
    aboutViewModel: AboutViewModel,
) {

    val uiState by aboutViewModel.aboutUiState.collectAsState()

    val scrollState = rememberScrollState()

    if (uiState.openDialog1) {
        PopUp1(
            setOpenDialog = { aboutViewModel.setOpenDialog1(it) },
            openNextDialog = {
                aboutViewModel.postEmail()
                aboutViewModel.setOpenDialog2(true)
            },
            onEmailChange = { aboutViewModel.setEmail(it) },
            email = uiState.emailAddress,
        )
    }
    if (uiState.openDialog2) {
        PopUp2(
            setOpenDialog = { aboutViewModel.setOpenDialog2(it) },
            emailAddress = uiState.emailAddress,
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Info(openPopUp = { aboutViewModel.setOpenDialog1(true) })
        Spacer(modifier = modifier.height(50.dp))
        Pictures(navigationType = navigationType)
        Spacer(modifier = modifier.height(40.dp))
    }
}

@Composable
fun Info(
    modifier: Modifier = Modifier,
    openPopUp: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = stringResource(R.string.about_screen_title),
            fontSize = 50.sp,
            color = Color.DarkGray,
        )
        Spacer(modifier = modifier.height(22.dp))
        Text(
            text = stringResource(R.string.about_minimum_space),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_space_dimensions), fontSize = 18.sp)
        Spacer(modifier = modifier.height(22.dp))
        Text(
            text = stringResource(R.string.about_requirements),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_on_site_person), fontSize = 18.sp)
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_power_outlet), fontSize = 18.sp)
        Spacer(modifier = modifier.height(22.dp))
        Text(
            text = stringResource(R.string.about_passage),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 22.sp
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_narrowest_point), fontSize = 18.sp)
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_minimum_width), fontSize = 18.sp)
        Spacer(modifier = modifier.height(8.dp))
        Text(text = stringResource(R.string.about_minimum_width_value), fontSize = 18.sp)
        Spacer(modifier = modifier.height(35.dp))
        Button(
            onClick = openPopUp,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC8A86E),
                disabledContainerColor = Color(0xFFf8f1e4),
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
        ) {
            Text(text = stringResource(R.string.about_more_info), fontSize = 22.sp)
        }
    }
}

@Composable
fun PopUp1(
    modifier: Modifier = Modifier,
    setOpenDialog: (Boolean) -> Unit,
    openNextDialog: () -> Unit,
    onEmailChange: (String) -> Unit,
    email: String
) {
    Dialog(onDismissRequest = { setOpenDialog(false) }) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0XFFf2e6ce),
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.about_popup1_title),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = modifier.height(15.dp))
                InputVeld(
                    label = stringResource(id = R.string.about_email_label),
                    value = email,
                    onChange = onEmailChange
                )
                Spacer(modifier = modifier.height(15.dp))
                Button(
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
                    Text(text = stringResource(id = R.string.about_continue))
                }
                Spacer(modifier = modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun PopUp2(
    modifier: Modifier = Modifier, setOpenDialog: (Boolean) -> Unit, emailAddress: String
) {
    Dialog(onDismissRequest = { setOpenDialog(false) }) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0XFFf2e6ce),
        ) {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.about_popup2_title),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = emailAddress,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = modifier.height(30.dp))
                Button(
                    onClick = { setOpenDialog(false) },
                    modifier = modifier
                        .width(100.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor,
                        disabledContainerColor = DisabledButtonColor,
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                ) {
                    Text(text = stringResource(id = R.string.about_close))
                }
                Spacer(modifier = modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun InputVeld(
    modifier: Modifier = Modifier, label: String, value: String, onChange: (String) -> Unit,
) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(0XFFD3B98B)) },
        value = value,
        onValueChange = onChange,
        modifier = modifier
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
fun Pictures(navigationType: ReplyNavigationType) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bigImg: Dp
        val smallImg: Dp
        val rowHeight: Dp

        when (navigationType) {
            ReplyNavigationType.NAVIGATION_RAIL -> {
                bigImg = 450.dp
                smallImg = 200.dp
                rowHeight = 180.dp
            }

            ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                bigImg = 600.dp
                smallImg = 425.dp
                rowHeight = 300.dp
            }

            else -> {
                bigImg = 200.dp
                smallImg = 115.dp
                rowHeight = 100.dp
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bigImg)
        ) {
            Image(
                painter = painterResource(R.drawable.foto3),
                contentDescription = stringResource(id = R.string.about_foodtruck_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(smallImg)
            ) {
                Image(
                    painter = painterResource(R.drawable.foto4),
                    contentDescription = stringResource(id = R.string.about_people_pouring_drinks_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(smallImg)
            ) {
                Image(
                    painter = painterResource(R.drawable.foto7),
                    contentDescription = stringResource(id = R.string.about_poured_glass_image_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(smallImg)
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