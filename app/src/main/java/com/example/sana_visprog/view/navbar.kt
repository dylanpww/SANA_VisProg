package com.example.sana_visprog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sana_visprog.repository.UserPreferences
import com.example.sana_visprog.routing.Screen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun NavBar(navController: NavController,
           userPreferences : UserPreferences) {

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 40.dp)
                .background(
                    color = Color(0xFFABB1CA),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val homeActive = currentRoute == "home" || currentRoute == "HOME"
                NavItem(
                    icon = Icons.Outlined.Home,
                    isActive = homeActive,
                    onClick = {
                        if (!homeActive) navController.navigate(Screen.HOME.name)
                    }
                )

                val planActive = currentRoute == "plan" || currentRoute == "PLAN"
                NavItem(
                    icon = Icons.Outlined.BookmarkBorder,
                    isActive = planActive,
                    onClick = {
                        if (!planActive) navController.navigate(Screen.PLAN.name)
                    }
                )

                val profileActive = currentRoute == "profile" || currentRoute == "PROFILE"
                NavItem(
                    icon = Icons.Outlined.Person,
                    isActive = profileActive,
                    onClick = {
                        if (!profileActive) {
                            scope.launch {
                                val token = userPreferences.authToken.first()
                                if (token.isNullOrBlank()) {
                                    navController.navigate(Screen.LOGIN.name) {
                                        popUpTo(0)
                                    }
                                } else {
                                    navController.navigate(Screen.PROFILE.name)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavItem(
    icon: ImageVector,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = if (isActive) Color(0xFF0F115E) else Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

//@Composable
//@Preview(showSystemUi = true, showBackground = true)
//fun NavBarPreview() {
//    NavBar(navController = rememberNavController())
//}