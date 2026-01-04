package com.example.sana_visprog.view.plan


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sana_visprog.view.destination.DestinationCard
import com.example.sana_visprog.viewmodel.PlanViewModel

@Composable
fun SelectDestinationScreen(
    navController: NavController,
    planId: Int,
    viewModel: PlanViewModel = viewModel(factory = PlanViewModel.Factory)
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDestinations()
    }

    Scaffold(
        topBar = {

            Box(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF0F115E)),
//                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 20.dp, top = 20.dp)
                    )
                }
                Text(
                    text = "Kita Pergi ke SANA",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 20.dp)
                )
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            if (viewModel.isLoadingDestinations) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF0F115E)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.allDestinations) { destination ->

                        Box(contentAlignment = Alignment.TopEnd) {
                            DestinationCard(
                                destination = destination,
                                onClick = {
                                }
                            )
                            IconButton(
                                onClick = {
                                    viewModel.addDestinationToPlan(planId, destination.id)
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(36.dp)
                                    .background(Color.White, CircleShape)
                                    .clip(CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add to Plan",
                                    tint = Color(0xFF0F115E)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}