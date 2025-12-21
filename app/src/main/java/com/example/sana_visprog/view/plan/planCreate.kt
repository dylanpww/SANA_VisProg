package com.example.sana_visprog.view.plan


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sana_visprog.viewmodel.CreatePlanState
import com.example.sana_visprog.viewmodel.PlanViewModel


@Composable
fun CreatePlanScreen(
    viewModel: PlanViewModel = viewModel(factory = PlanViewModel.Factory),
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val uiState = viewModel.createPlanState

    LaunchedEffect(uiState) {
        if (uiState is CreatePlanState.Success) {
            navController.navigateUp()
            viewModel.resetCreateState()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
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
                        text = "New Trip",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF00126A),
                        modifier = Modifier.padding(vertical = 40.dp)
                    )
                }

                Text(
                    text = "Trip name",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF00126A)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = {
                        Text(
                            text = "Boys Trip",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppins
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )

                Text(
                    text = "Description",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF00126A),
                    modifier = Modifier.padding(top = 20.dp)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = {
                        Text(
                            text = "This is going to be the best trip ever!",
                            color = Color.LightGray,
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppins
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )

            Box(
                modifier = Modifier.padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        if (name.isNotEmpty()) {
                            viewModel.createPlan(name, description)
                        }
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
                    if (uiState is CreatePlanState.Loading) {
                        CircularProgressIndicator(
                            color = Color(0xFF00126A),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text(
                            text = "Save",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}