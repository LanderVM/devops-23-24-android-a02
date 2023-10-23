package com.example.templateapplication.ui.screens.overpage

import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.templateapplication.R
import com.example.templateapplication.ui.theme.DisabledButtonColor
import com.example.templateapplication.ui.theme.MainColor
import org.json.JSONObject


@Composable
fun EmailForInformationScreen (
    modifier: Modifier = Modifier
) {

    var email by remember {mutableStateOf("")}

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Gelieve hieronder uw email in te vullen en op de knop \"verstuur\" te drukken om meer informatie betreffende de foodtruck te ontvangen.",
            modifier = Modifier.padding(horizontal = 40.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        //InputVeld(label = "email", value = email, onChange = {email=it}) {

        //}
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {},
            enabled = !(email.isBlank()||email.isEmpty()),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainColor,
                disabledContainerColor = DisabledButtonColor,
                contentColor = Color.White,
                disabledContentColor = Color.White
            ),
        ) {
            Text(text= "Verstuur",)
        }
    }
}
/*
private fun sendEmail (email:String) {

    var resp:String = ""
    var error:String = ""

    val siteUrl = ""

    var obj:JSONObject = JSONObject().put("email",email)

    //val cache = DiskBasedCache( 1024 * 1024)

    val network = BasicNetwork(HurlStack())

    //val queue = Volley.newRequestQueue()

    val jsonObjRequest = JsonObjectRequest(
        Request.Method.PUT, siteUrl, obj,
        Response.Listener{ jsonObject ->
            // handle JSON response
            resp = jsonObject.toString()
        },
        {volleyError ->
            // handle error
            error = volleyError.toString()
        })
    //queue.add(jsonObjRequest)
}*/
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputVeld(
    modifier: Modifier = Modifier,label:String,value: String,onChange:(String)->Unit,makeEmpty:()->Unit
) {

    OutlinedTextField(
        label = { Text(text = label, color = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))) },
        value = value,
        onValueChange =onChange,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 75.dp)
            .height(75.dp)
            .padding(horizontal = 40.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
            unfocusedBorderColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter)))
        ),
        trailingIcon = {
            IconButton(
                onClick = makeEmpty,
                enabled = !value.isEmpty(),
                /*colors= IconButtonColors(
                    containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    contentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichter))),
                    disabledContainerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                    disabledContentColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichts))),
                ),*/
            ) {
                Icon(
                    Icons.Default.Clear,contentDescription = null,tint=Color(android.graphics.Color.parseColor(
                        stringResource(id = R.string.lichter)
                    )))
            }
        },
    )
}*/
