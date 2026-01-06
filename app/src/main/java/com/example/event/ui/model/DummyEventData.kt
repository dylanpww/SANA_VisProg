package com.example.event.ui.model

import com.example.event.data.dto.Event
import com.example.event.data.dto.Review

object DummyEventData {
    val eventDetail = Event(
        id = 1,
        name = "Destination 1",
        location = "Pakal, Surabaya",
        photoUrl = "",
        rating = 5.0f,
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    )

    val reviews = listOf(
        Review(
            id = 1,
            userName = "Agus",
            rating = 5.0f,
            comment = "Such a good place i can heal myself"
        ),
        Review(
            id = 2,
            userName = "Aliong",
            rating = 5.0f,
            comment = "Such a good place i can heal myself"
        )
    )

    val eventList = listOf(
        Event(
            id = 1,
            name = "Lomba Burung",
            location = "24 Januari 2027",
            photoUrl = "",
            rating = 5.0f,
            description = "Kicawkan burungmu di tempat kami dan menangkan hadiahnya"
        ),
        Event(
            id = 2,
            name = "Lomba Burung",
            location = "24 Januari 2027",
            photoUrl = "",
            rating = 5.0f,
            description = "Kicawkan burungmu di tempat kami dan menangkan hadiahnya"
        ),
        Event(
            id = 3,
            name = "Lomba Burung",
            location = "24 Januari 2027",
            photoUrl = "",
            rating = 5.0f,
            description = "Kicawkan burungmu di tempat kami dan menangkan hadiahnya"
        )
    )
}
