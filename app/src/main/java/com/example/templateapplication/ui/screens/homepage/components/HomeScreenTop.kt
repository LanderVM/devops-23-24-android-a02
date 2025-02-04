package com.example.templateapplication.ui.screens.homepage.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.templateapplication.R
import com.example.templateapplication.ui.utils.ReplyNavigationType

/**
 * Composable function for rendering the top section of the Home screen.
 *
 * This function creates the top section of the Home screen, which includes a background image,
 * a title, and a menu icon. The appearance of this section can change based on the navigation type.
 *
 * @param navigationType The type of navigation being used, affecting the layout and font size.
 * @param modifier Modifier to be applied for customization.
 * @param openDrawer Callback function to be executed when the menu icon is clicked.
 */
@Composable
fun HomeScreenTop(
    navigationType: ReplyNavigationType,
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit = {}
) {
    val blanchePadding: Dp
    val blancheFontSize: TextUnit
    when (navigationType) {
        ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            blanchePadding = 130.dp
            blancheFontSize = 100.sp
        }

        else -> {
            blanchePadding = 70.dp
            blancheFontSize = 80.sp
        }
    }
    val image = painterResource(R.drawable.homescreen_background)
    Column {
        Box(
            modifier = modifier
                .fillMaxHeight(0.4f)
                .testTag("homeScreenTop")
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(8.dp)
                )

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    IconButton(
                        onClick = openDrawer,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(id = R.string.appbar_menu),
                            tint = Color.White,
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontFamily = FontFamily.SansSerif,
                            fontSize = blancheFontSize,
                            color = Color.White,
                            modifier = Modifier.padding(top = blanchePadding)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.home_info),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}