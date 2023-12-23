package com.example.templateapplication.navigation.navidrawer

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.templateapplication.navigation.NavigationRoutes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    navController: NavHostController,
    selectedItemIndex: Int,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Context,
) {
    val navItems = listOf(
        NavigationItem(
            title = NavigationRoutes.Home.getString(context),
            to = NavigationRoutes.Home.name,
        ),
        NavigationItem(
            title = NavigationRoutes.About.getString(context),
            to = NavigationRoutes.About.name,
        ),
        NavigationItem(
            title = NavigationRoutes.Formulas.getString(context),
            to = NavigationRoutes.Formulas.name,
        ),
        NavigationItem(
            title = NavigationRoutes.ExtrasOverview.getString(context),
            to = NavigationRoutes.ExtrasOverview.name,
        ),
        NavigationItem(
            title = NavigationRoutes.GuidePrice.getString(context),
            to = NavigationRoutes.GuidePrice.name,
        ),
    )

    ModalDrawerSheet(
        drawerContentColor = Color.White,
        modifier = Modifier
            .alpha(0.9f),
    ) {
        Spacer(
            modifier = Modifier.height(16.dp),
        )

        navItems.forEachIndexed { index, item ->
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally),
                color = Color.DarkGray
            )

            NavigationDrawerItem(
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = Color.Transparent,
                ),
                label = {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = item.title.uppercase(),
                            maxLines = 2,
                            fontSize = 30.sp,
                            lineHeight = 35.sp,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
                selected = index == selectedItemIndex,
                onClick = {
                    navController.navigate(item.to)
                    scope.launch {
                        drawerState.close()
                    }
                },
                modifier = Modifier
                    .height(80.dp)
                    .padding(NavigationDrawerItemDefaults.ItemPadding),
            )
        }
    }
}

@Preview
@Composable
fun NavigationDrawerPreview() {
    val navController = rememberNavController()
    val selectedItemIndex = 1
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val context = LocalContext.current

    NavigationDrawer(
        navController = navController,
        selectedItemIndex = selectedItemIndex,
        scope = rememberCoroutineScope(),
        drawerState = drawerState,
        context = context,
    )
}
