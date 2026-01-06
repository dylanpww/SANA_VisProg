package com.example.sana_visprog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.view.plan.poppins
import com.example.sana_visprog.viewmodel.ProfileViewModel

val DarkBlue = Color(0xFF0F115F)
val LightGreyBg = Color(0xFFF3F4F6)

@Composable
fun ProfileView(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)
) {
    LaunchedEffect(viewModel.isLoggedOut.value) {
        if (viewModel.isLoggedOut.value) {
            navController.navigate(Screen.STARTING.name) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    ProfileContent(
        userName = viewModel.userName.value,
        onLogout = { viewModel.logout() }
    )
}
@Composable
fun ProfileContent(
    userName: String,
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreyBg)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = DarkBlue,
                    shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                val initial = if (userName.isNotEmpty()) userName.take(1).uppercase() else "?"
                Text(
                    text = initial,
                    fontSize = 48.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Hai, $userName!",
                fontSize = 24.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome back to SANA",
                fontSize = 14.sp,
                fontFamily = poppins,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Account Settings", fontWeight = FontWeight.Bold,fontFamily = poppins, color = DarkBlue)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Edit Profile", color = Color.Gray, fontFamily = poppins,modifier = Modifier.padding(vertical = 8.dp))
                    Divider(color = Color(0xFFEEEEEE))
                    Text("Change Password", color = Color.Gray,fontFamily = poppins, modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 140.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Log Out",
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileViewPreview() {
    ProfileContent(
        userName = "Budi Santoso",
        onLogout = {}
    )
}