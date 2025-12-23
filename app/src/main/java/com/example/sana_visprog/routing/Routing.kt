package com.example.sana_visprog.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sana_visprog.view.HomeView
import com.example.sana_visprog.view.StartingPage
import com.example.sana_visprog.view.categories.CategoryDetailView
import com.example.sana_visprog.view.categories.CreateCategoryView

enum class Screen {
    STARTING,
    HOME,
    PLANNER,
    PROFILE,
    CREATE_CATEGORY,
    CATEGORY_DETAIL
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

        composable(Screen.CREATE_CATEGORY.name) {
            CreateCategoryView(navController = navController)
        }

        composable(
            route = "${Screen.CATEGORY_DETAIL.name}/{id}"
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0

            CategoryDetailView(
                navController = navController,
                categoryId = id
            )
        }
    }
}