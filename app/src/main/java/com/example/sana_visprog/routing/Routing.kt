package com.example.sana_visprog.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sana_visprog.view.HomeView
import com.example.sana_visprog.view.StartingPage

enum class Screen {
    STARTING,
    HOME,
    PLANNER,
    PROFILE
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.STARTING.name
    ) {
        composable(Screen.STARTING.name) {
            StartingPage(navController = navController)
        }

        composable(Screen.HOME.name) {
            HomeView(navController = navController)
        }

    }

}