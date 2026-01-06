package com.example.event.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.event.data.dto.Event
import com.example.event.data.dto.Review
import com.example.event.ui.theme.EventTheme
import com.example.event.ui.viewmodel.EventDetailViewModel

@Composable
fun EventDetailScreen(
    eventId: Int = 1,
    viewModel: EventDetailViewModel = viewModel { EventDetailViewModel(eventId) },
    onBackClick: () -> Unit = {},
    onSeeEventsClick: () -> Unit = {},
    onAddReviewClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val event = viewModel.event.collectAsState().value
    val reviews = viewModel.reviews.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    Scaffold(
        containerColor = Color(0xFFEDEFF6),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEventClick,
                shape = RoundedCornerShape(18.dp),
                containerColor = Color(0xFF0A0D5B),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        EventDetailContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            event = event,
            isLoading = isLoading,
            errorMessage = errorMessage,
            onBackClick = onBackClick,
            onAddEventClick = onAddEventClick,
            onClearError = { viewModel.clearError() },
            cardCount = maxOf(reviews.size, 3)
        )
    }
}

@Composable
private fun EventDetailContent(
    modifier: Modifier,
    event: Event,
    isLoading: Boolean,
    errorMessage: String?,
    onBackClick: () -> Unit,
    onAddEventClick: () -> Unit,
    onClearError: () -> Unit,
    cardCount: Int
) {
    Column(
        modifier = modifier
            .background(Color(0xFFEDEFF6))
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF0A0D5B),
                    strokeWidth = 4.dp
                )
            }
            return@Column
        }

        if (errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .background(Color(0xFFB71C1C), shape = RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.White,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = onClearError,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Retry",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Hero header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A0D5B))
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Text(
                    text = if (event.name.isNotBlank()) event.name else "Destination 1",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (event.location.isNotBlank()) event.location else "Some events in this location",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onAddEventClick,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC6CBD8),
                contentColor = Color(0xFF0A0D5B)
            )
        ) {
            Text(
                text = "Add Events",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        repeat(cardCount) {
            EventCardItem(event)
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

@Composable
private fun EventCardItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .heightIn(min = 180.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB8C4E6)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = if (event.name.isNotBlank()) event.name else "Lomba Burung",
                color = Color(0xFF0A0D5B),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (event.description.isNotBlank()) event.description else "Kicawkan burungmu di tempat kami dan menangkan hadiahnya",
                color = Color(0xFF0A0D5B),
                fontSize = 16.sp,
                lineHeight = 22.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE6E7EF))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "24 Januari 2027",
                    color = Color(0xFF0A0D5B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        containerColor = Color(0xFFB8B8D1),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(32.dp)
                )
            },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1A237E),
                unselectedIconColor = Color(0xFF1A237E),
                indicatorColor = Color.Transparent
            )
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Bookmarks",
                    modifier = Modifier.size(32.dp)
                )
            },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1A237E),
                unselectedIconColor = Color(0xFF1A237E),
                indicatorColor = Color.Transparent
            )
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            },
            selected = false,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1A237E),
                unselectedIconColor = Color(0xFF1A237E),
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EventDetailPreview() {
    EventTheme {
        EventDetailContent(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            event = Event(
                id = 1,
                name = "Destination 1",
                location = "Some events in this location",
                photoUrl = "",
                rating = 5f,
                description = "Kicawkan burungmu di tempat kami dan menangkan hadiahnya"
            ),
            isLoading = false,
            errorMessage = null,
            onBackClick = {},
            onAddEventClick = {},
            onClearError = {},
            cardCount = 3
        )
    }
}

