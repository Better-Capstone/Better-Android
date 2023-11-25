package com.ssu.better.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssu.better.R
import com.ssu.better.ui.theme.BetterAndroidTheme
import com.ssu.better.ui.theme.BetterColors

@Composable
fun BetterBottomBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val navItems = listOf(Screen.Home, Screen.Search.Main, Screen.MyPage)
    var selectedScreen by remember { mutableStateOf(navItems.first()) }
    val context = LocalContext.current

    LaunchedEffect(navHostController.currentDestination?.route) {
        val route = navHostController.currentDestination?.route
        navItems.forEach {
            if (it.route == route) selectedScreen = it
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = BetterColors.White,
            shadowElevation = 2.dp,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .width(screenWidth / 2),
            ) {
                navItems.forEach {
                    getBottomTab(
                        screen = it,
                        isSelected = it == selectedScreen,
                        onClick = {
                            navHostController.navigate(it.route) {
                                popUpTo(selectedScreen.route) {
                                    inclusive = true
                                }
                            }
                            selectedScreen = it
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
fun BetterTab(
    icon: Int,
    label: String,
    selected: Boolean = false,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val selectedColor = if (selected) BetterColors.Primary50 else BetterColors.Gray90

    Column(
        modifier = modifier.clickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = selectedColor,
            modifier = Modifier.padding(top = 10.dp).size(20.dp),
        )

        Text(
            text = label,
            style = BetterAndroidTheme.typography.subtitle,
            color = selectedColor,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp),
        )
    }
}

@Composable
fun getBottomTab(screen: Screen, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier) {
    val context = LocalContext.current

    return when (screen) {
        Screen.MyPage -> BetterTab(
            selected = isSelected,
            icon = R.drawable.ic_person,
            label = context.getString(R.string.tab_mypage),
            onClick = onClick,
            modifier = modifier,
        )

        Screen.Search.Main -> BetterTab(
            selected = isSelected,
            icon = R.drawable.ic_search,
            label = context.getString(R.string.tab_search),
            onClick = onClick,
            modifier = modifier,
        )

        else -> BetterTab(
            selected = isSelected,
            icon = R.drawable.ic_home,
            label = context.getString(R.string.tab_home),
            onClick = onClick,
            modifier = modifier,
        )
    }
}
