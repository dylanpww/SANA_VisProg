package com.example.sana_visprog.view.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import com.example.sana_visprog.routing.Screen
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigationevent.compose.rememberNavigationEventState
import com.example.sana_visprog.viewmodel.AuthUiState
import com.example.sana_visprog.viewmodel.RegisterViewModel

@Composable
fun UserSignUpView(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel(factory = RegisterViewModel.Factory)
){
    val registerState by viewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(registerState) {
        when (val state = registerState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, "BERHASIL! membuat akun", Toast.LENGTH_LONG).show()
                viewModel.resetState()
                navController.navigate(Screen.LOGIN.name)
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F115F)),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE6E7F0))

        ){
            Column (
                modifier = Modifier
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    "Sign Up",
                    color = Color(0xFF0F115F),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(
                    value = viewModel.usernameInput,
                    onValueChange = {viewModel.onUsernameChange(it)},
                    label = { Text("Username") },
                    shape = RoundedCornerShape(50)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.emailInput,
                    onValueChange = {viewModel.onEmailChange(it)},
                    label = { Text("Email") },
                    shape = RoundedCornerShape(50)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = viewModel.passwordInput,
                    onValueChange = {viewModel.onPasswordChange(it)},
                    label = { Text("Password") },
                    shape = RoundedCornerShape(50)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text("Already have an account?")
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                navController.navigate(Screen.LOGIN.name)
                            }
                        )
                ){
                    Text("Log In", color = Color(0xFF0F115F), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        viewModel.register()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F115F),
                        contentColor = Color.White
                    ),
                    enabled = registerState !is AuthUiState.Loading
                ) {
                    Text("Sign Up")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewUserSignUpView(){
    //UserSignUpView()
}