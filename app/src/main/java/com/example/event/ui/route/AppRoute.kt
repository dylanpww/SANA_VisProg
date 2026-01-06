package com.example.event.ui.route

sealed class AppRoute(val route: String) {
    data object EventDetail : AppRoute("event_detail")
    data object EventList : AppRoute("event_list")
    data object AddEvent : AppRoute("add_event")
    data object AddReview : AppRoute("add_review")
}
