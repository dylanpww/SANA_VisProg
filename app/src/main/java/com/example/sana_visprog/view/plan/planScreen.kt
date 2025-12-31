package com.example.sana_visprog.view.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sana_visprog.R
import com.example.sana_visprog.dto.Plan.PlanItem
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.viewmodel.PlanUiState
import com.example.sana_visprog.viewmodel.PlanViewModel

val poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
)


@Composable
fun PlanScreen(
    navController: NavHostController,
    viewModel: PlanViewModel = viewModel(factory = PlanViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getPlans()
    }
    val uiState = viewModel.planUiState
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp)
            ) {
                when (uiState) {
                    is PlanUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFF0F115E))
                        }
                    }

                    is PlanUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Gagal memuat data. Cek Backend.",
                                fontFamily = poppins,
                                color = Color.Red
                            )
                        }
                    }

                    is PlanUiState.Success -> {
                        PlanList(
                            plans = uiState.plans,
                            onDeleteClick = { planId -> viewModel.deletePlan(planId) },
                            onItemClick = { planId ->
                                val selected = uiState.plans.find { it.id == planId }
                                if (selected != null){
                                    navController.navigate("${Screen.PLAN_DETAIL.name}/${selected.id}" +
                                    "?name=${selected.name}" +
                                    "&desc=${selected.description ?: ""}")
                                }

                            }
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = { navController.navigate(Screen.CREATE_PLAN.name) },
            containerColor = Color(0xFF0F115E),
            contentColor = Color.White,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun PlanList(
    plans: List<PlanItem>,
    onDeleteClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit

) {
    LazyColumn(
        contentPadding = PaddingValues(top = 30.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(plans) { plan ->
            PlanCard(
                plan = plan,
                onDeleteClick = onDeleteClick,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun PlanCard(
    plan: PlanItem,
    onDeleteClick: (Int) -> Unit,
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFB6BBDE),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
            .clickable {
                onItemClick(plan.id)
            }
    ) {

        Text(
            text = plan.name,
            fontSize = 20.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        if (!plan.description.isNullOrEmpty()) {
            Text(
                text = plan.description,
                fontFamily = poppins,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        Button(
            onClick = { onDeleteClick(plan.id) },
            modifier = Modifier
                .padding(top = 10.dp)
                .width(90.dp)
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD7D9E7),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Delete",
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}