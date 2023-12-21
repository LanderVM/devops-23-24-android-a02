package com.example.templateapplication.ui.screens.homepage.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.templateapplication.R

@Composable
fun FormulaCard(
    modifier: Modifier = Modifier,
    title: String,
    image: String = "",
    imageTxt: String = "",
    onButtonClicked: () -> Unit
) {

    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(4290025315))
                    .padding(2.dp)
            ) {
                if (image.isBlank())
                    Image(
                        painter = painterResource(id = R.drawable.foto7),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                    )
                else
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .scale(Scale.FILL)
                            .build(),
                        placeholder = painterResource(id = R.drawable.foto7),
                        contentDescription = imageTxt,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxSize()
                    )
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = onButtonClicked,
                border = BorderStroke(3.dp, Color(4290025315)),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(4290025315),
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(stringResource(R.string.nav_formulaCard))
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
