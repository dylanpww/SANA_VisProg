package com.example.sana_visprog.routing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.repository.UserPreferences
import com.example.sana_visprog.view.HomeView
import com.example.sana_visprog.view.NavBar
import com.example.sana_visprog.view.ProfileView
import com.example.sana_visprog.view.StartingPage
import com.example.sana_visprog.view.destination.DestinationDetailView
import com.example.sana_visprog.view.categories.CategoryDetailView
import com.example.sana_visprog.view.categories.CreateCategoryView
import com.example.sana_visprog.view.destination.CreateDestinationView
import com.example.sana_visprog.view.plan.CreatePlanScreen
import com.example.sana_visprog.view.plan.PlanScreen
import com.example.sana_visprog.view.plan.SelectDestinationScreen
import com.example.sana_visprog.view.plan.UpdatePlanScreen
import com.example.sana_visprog.view.user.UserLoginView
import com.example.sana_visprog.view.user.UserSignUpView
import com.example.sana_visprog.viewmodel.LoginViewModel

enum class Screen {
    STARTING,
    LOGIN,
    REGISTER,
    HOME,
    PLANNER,
    PROFILE,
    SELECT_DESTINATION,
    CREATE_CATEGORY,
    CATEGORY_DETAIL,
    CREATE_PLAN,
    PLAN,
    PLAN_DETAIL,
    DESTINATION_DETAIL,
    ADD_DESTINATION
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val userPreferences = (context.applicationContext as SANAVisProgApplication).container.userPreferences
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.HOME.name,
        Screen.PLAN.name,
        Screen.PROFILE.name,
        Screen.PLAN.name,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.STARTING.name
        ) {
            composable(Screen.STARTING.name) {
                StartingPage(navController = navController)
            }

            composable(Screen.LOGIN.name) {
                UserLoginView(
                    navController = navController
                )
            }

            composable(Screen.REGISTER.name) {
                UserSignUpView(
                    navController = navController
                )
            }

            composable(Screen.PROFILE.name) {
                ProfileView(navController = navController)
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

            composable(Screen.PLAN.name) {
                PlanScreen(navController = navController)
            }

            composable(Screen.CREATE_PLAN.name) {
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
            composable(
                route = "${Screen.SELECT_DESTINATION.name}/{planId}",
                arguments = listOf(navArgument("planId") { type = NavType.IntType })
            ) { backStackEntry ->
                val planId = backStackEntry.arguments?.getInt("planId") ?: 0

                SelectDestinationScreen(
                    navController = navController,
                    planId = planId
                )
            }

            composable(
                route = "${Screen.DESTINATION_DETAIL.name}/{destinationId}",
                arguments = listOf(
                    navArgument("destinationId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("destinationId") ?: 0

                DestinationDetailView(
                    navController = navController,
                    destinationId = id
                )
            }
            composable(Screen.ADD_DESTINATION.name) {
                CreateDestinationView(navController = navController)
            }
        }
        if (showBottomBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                NavBar(
                    navController = navController,
                    userPreferences = userPreferences
                )
            }
        }
    }
}