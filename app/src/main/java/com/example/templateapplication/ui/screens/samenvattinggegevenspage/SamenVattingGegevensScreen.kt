package com.example.templateapplication.ui.screens.samenvattinggegevenspage

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.ui.theme.MainColor
import com.example.templateapplication.ui.theme.MainLightestColor
import com.example.templateapplication.R

@Composable
fun SamenvattingGegevensScreen (
    modifier: Modifier = Modifier,
    navigateEventGegevens:()->Unit,
    navigateContactGegevens:()->Unit,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        HeadOfPage()
        Spacer(modifier = Modifier.height(20.dp))
        Navigation(
            navigateContactGegevens=navigateContactGegevens,
            navigateEventGegevens = navigateEventGegevens,
        )
        Spacer(modifier = Modifier.height(25.dp))
        Divider(color = Color.LightGray, thickness = 4.dp, modifier = Modifier.padding(horizontal = 15.dp))
        Spacer(modifier = Modifier.height(25.dp))
        EventGegevens()
    }

}
@Composable
fun HeadOfPage(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text= "Overzicht",
            textAlign = TextAlign.Center,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            color = MainColor,
            progress = 1.00f,
            trackColor = MainLightestColor,
        )
    }
}
@Composable
fun Navigation (
    modifier: Modifier = Modifier,
    navigateEventGegevens:()->Unit,
    navigateContactGegevens:()->Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text= "Ga naar",
            textAlign = TextAlign.Left,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            fontSize = 30.sp,
            color = Color.Black,
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Evenement") ,
                onClick = {
                    navigateEventGegevens()
                },
                style = TextStyle(fontSize = 22.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Contact en facturatie gegevens"),
                onClick = {
                    navigateContactGegevens()
                },
                style = TextStyle(fontSize = 22.sp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filled_circle),
                contentDescription = "een gevulde circel",
                modifier = Modifier.size(13.dp,13.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            ClickableText(
                text = AnnotatedString("Extra materieel"),
                onClick = {},
                style = TextStyle(fontSize = 22.sp)
            )
        }

    }
}

@Composable
fun EventGegevens(
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text= "Evenement",
            textAlign = TextAlign.Start,
            modifier= Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MainColor,
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Datum",fontSize = 18.sp)},
            supportingContent = {Text(text="23/44/5555",fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Locatie",fontSize = 18.sp)},
            supportingContent = {Text(text="ergens",fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ListItem(
            headlineContent = {Text(text="Type evenement", fontSize = 18.sp)},
            supportingContent = {Text(text="een Japanse douche",fontSize = 16.sp)},
            colors = ListItemDefaults.colors(
                containerColor = Color(android.graphics.Color.parseColor(stringResource(id = R.string.lichterder)))
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        
    }
}