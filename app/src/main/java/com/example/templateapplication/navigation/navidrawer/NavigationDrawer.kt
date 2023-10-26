package com.example.templateapplication.navigation.navidrawer

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.templateapplication.navigation.NavigationRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    navController: NavHostController,
    selectedItemIndex: Int,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Context
) {
    var selectedItemIdx = selectedItemIndex

    val navItems = listOf(
        NavigationItem(
            title = NavigationRoutes.home.getString(context),
            to = NavigationRoutes.home.name
        ),
        NavigationItem(
            title = NavigationRoutes.over.getString(context),
            to = NavigationRoutes.over.name
        ),
        NavigationItem(
            title = NavigationRoutes.evenementGegevens.getString(context),
            to = NavigationRoutes.evenementGegevens.name
        ),
        NavigationItem(
            title = NavigationRoutes.formules.getString(context),
            to = NavigationRoutes.formules.name
        ),
    )

    ModalDrawerSheet(
        drawerContentColor = Color.White,
        modifier = Modifier
            .alpha(0.9f)
    ) {
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        navItems.forEachIndexed { index, item ->
            Divider(
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )
            val isSelected = index == selectedItemIndex

            NavigationDrawerItem(
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Transparent
                ),
                label = {
                    Text(
                        text = item.title.uppercase(),
                        fontSize = 40.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = index == selectedItemIndex,
                onClick = {
                    navController.navigate(item.to)
                    selectedItemIdx = index
                    scope.launch {
                        drawerState.close()
                    }
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

