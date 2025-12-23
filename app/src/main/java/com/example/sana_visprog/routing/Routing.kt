package com.example.sana_visprog.routing

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sana_visprog.view.HomeView
import com.example.sana_visprog.view.StartingPage
import com.example.sana_visprog.view.categories.CategoryDetailView
import com.example.sana_visprog.view.categories.CreateCategoryView
import com.example.sana_visprog.view.plan.CreatePlanScreen
import com.example.sana_visprog.view.plan.PlanScreen
import com.example.sana_visprog.view.plan.UpdatePlanScreen

enum class Screen {
    STARTING,
    HOME,
    PLANNER,
    PROFILE,
    CREATE_CATEGORY,
    CATEGORY_DETAIL,
    CREATE_PLAN,
    PLAN,
    PLAN_DETAIL
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.PLAN.name
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

        composable(Screen.PLAN.name){
            PlanScreen(navController = navController)
        }

        composable(Screen.CREATE_PLAN.name){
            CreatePlanScreen(navController = navController)
        }

        composable(
            route = "${Screen.PLAN_DETAIL.name}/{planId}?name={name}&desc={description}",
            arguments = listOf(
                navArgument("planId") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType; defaultValue = "" },
                navArgument("description") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val desc = backStackEntry.arguments?.getString("description") ?: ""

            UpdatePlanScreen(
                navController = navController,
                planId = planId,
                oldName = name,
                oldDescription = desc
            )
        }
    }
}