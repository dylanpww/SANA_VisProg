package com.example.sana_visprog.view.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sana_visprog.dto.Plan.toPlan
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.viewmodel.PlanUiState
import com.example.sana_visprog.viewmodel.PlanViewModel
import com.example.sana_visprog.viewmodel.UpdatePlanState

@Composable
fun UpdatePlanScreen(
    viewModel: PlanViewModel = viewModel(factory = PlanViewModel.Factory),
    navController: NavHostController,
    planId: Int,
    oldName: String,
    oldDescription: String
) {
    LaunchedEffect(planId) {
        viewModel.getPlanDetail(planId)
    }

    var name by remember { mutableStateOf(oldName) }
    var description by remember { mutableStateOf(oldDescription) }

    val updateState = viewModel.updatePlanState
    val detailState = viewModel.planDetailUiState
    val currentPlan = viewModel.currentPlanDetail

    LaunchedEffect(updateState) {
        if (updateState is UpdatePlanState.Success) {
            navController.navigateUp()
            viewModel.resetUpdateState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item(
                span = { GridItemSpan(2) }
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .height(130.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                            .background(Color(0xFF0F115E)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Kita Pergi ke SANA",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Update Trip",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = Color(0xFF00126A),
                                modifier = Modifier.padding(vertical = 40.dp)
                            )
                        }

                        Text(
                            "Trip name",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00126A)
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        )

                        Text(
                            "Description",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00126A),
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        )

                        Button(
                            onClick = {
                                if (name.isNotEmpty()) viewModel.updatePlan(
                                    planId,
                                    name,
                                    description
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFAFB5CF),
                                contentColor = Color(0xFF00126A)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (updateState is UpdatePlanState.Loading) {
                                CircularProgressIndicator(
                                    color = Color(0xFF00126A),
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text(
                                    "Save",
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )

                }
            }

            if (detailState is PlanUiState.Loading) {
                item(
                    span = {GridItemSpan(2)}
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF0F115E))
                    }
                }
            } else if (currentPlan != null) {

                item(
                    span = { GridItemSpan(2) }
                ) {
                    Text(
                        text = "Destinations List (${currentPlan.destinations.size})",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF00126A),
                        modifier = Modifier.padding(start = 30.dp, top = 20.dp, bottom = 10.dp)
                    )
                }
                item(
                    span = { GridItemSpan(2) }
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Button(
                            onClick = {
                                navController.navigate("${Screen.SELECT_DESTINATION.name}/$planId")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F115E)),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.height(50.dp)
                        ) {
                            Icon(Icons.Default.Add, null)
                            Text(" Add Destination")
                        }
                    }
                }

                items(currentPlan.destinations) { destination ->
                    Box{
                        PlanGridCard(
                            destination = destination,
                            onVisitedClick = { isChecked ->
                                viewModel.isVisited(destination.id, !isChecked)
                            },
                            onDeleteClick = {
                                viewModel.removeDestinationFromPlan(destination.id)
                            }
                        )
                    }
                }

                if (currentPlan.destinations.isEmpty()) {
                    item(
                        span = { GridItemSpan(2) }
                    ) {
                        Text(
                            text = "Empty, add more destinations to your list!.",
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 30.dp)
                        )
                    }
                }


            } else if (detailState is PlanUiState.Error) {
                item(
                    span = {GridItemSpan(2)}
                ) {
                    Text(
                        "Gagal memuat detail plan.",
                        color = Color.Red,
                        modifier = Modifier.padding(30.dp)
                    )
                }
            }
        }
    }
}
