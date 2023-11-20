package com.example.templateapplication.ui.screens.homepage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.templateapplication.R
import com.example.templateapplication.ui.commons.AlertPopUp
import com.example.templateapplication.ui.screens.homepage.components.FormuleCard
import com.example.templateapplication.ui.screens.homepage.components.HomeScreenTop

@Composable
fun HomeScreen(
    openDrawer: () -> Unit = {},
    onExtraNavigation: () -> Unit,
    onBasicNavigation: () -> Unit,
    onAllInNavigation: () -> Unit,
    onGevorderedNavigation: () -> Unit,
    onGuidePriceNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {
    var openAlertDialog by remember { mutableStateOf(true) }

    when {
        openAlertDialog -> {
            AlertPopUp(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false
                    onGuidePriceNavigation()
                },
                dialogTitle = "Snelle Inschatting",
                dialogText = "Wilt u een snelle prijsinschatting maken voor uw offerteaanvraag?",
                confirmText = "Ja",
                dismissText = "Ik wacht nog even"
            )
        }
    }

    val image1 = painterResource(id = R.drawable.foto4)
    val image2 = painterResource(id = R.drawable.foto5)
    val image3 = painterResource(id = R.drawable.foto8)
    val image4 = painterResource(id = R.drawable.foto7)

    LazyColumn {
        item {
            HomeScreenTop(openDrawer = openDrawer)
        }
        item {
            FormuleCard("Basic", image1, onButtonClicked = onBasicNavigation)
        }
        item {
            FormuleCard("All-In", image2, onButtonClicked = onAllInNavigation)
        }
        item {
            FormuleCard("Gevorderd", image3, onButtonClicked = onGevorderedNavigation)
        }
        item {
            FormuleCard("Extra Materiaal", image4, onButtonClicked = onExtraNavigation)
        }
    }
}
