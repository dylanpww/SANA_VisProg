package com.example.event

import android.os.Bundle
import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.event.ui.model.DummyEventData
import com.example.event.ui.route.AppRoute
import com.example.event.ui.theme.EventTheme
import com.example.event.ui.view.AddEventScreen
import com.example.event.ui.view.AddReviewScreen
import com.example.event.ui.view.EventDetailScreen
import com.example.event.ui.view.EventListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventTheme {
                val activity = LocalActivity.current
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRoute.EventDetail.route
                ) {
                    composable(AppRoute.EventDetail.route) { backStackEntry ->
                        EventDetailScreen(
                            eventId = 1,
                            onBackClick = { activity?.finish() },
                            onSeeEventsClick = {
                                navController.navigate(AppRoute.EventList.route) {
                                    launchSingleTop = true
                                }
                            },
                            onAddReviewClick = {
                                navController.navigate(AppRoute.AddReview.route)
                            },
                            onAddEventClick = {
                                navController.navigate(AppRoute.AddEvent.route)
                            }
                        )
                    }

                    composable(AppRoute.EventList.route) { backStackEntry ->
                        EventListScreen(
                            onBackClick = { navController.popBackStack() },
                            onAddEventClick = {
                                navController.navigate(AppRoute.AddEvent.route)
                            }
                        )
                    }

                    composable(AppRoute.AddEvent.route) {
                        AddEventScreen(
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable(AppRoute.AddReview.route) {
                        AddReviewScreen(
                            eventId = 1,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    EventTheme {
        EventDetailScreen(
            eventId = 1
        )
    }
}
