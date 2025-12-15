package com.example.sana_visprog.view.user

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sana_visprog.viewmodel.AuthUiState
import com.example.sana_visprog.viewmodel.LoginViewModel

@Composable
fun UserLoginView(
    viewModel: LoginViewModel
) {
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is AuthUiState.Success -> {
                Toast.makeText(context, "BERHASIL! Halo ${state.username}", Toast.LENGTH_LONG).show()
                viewModel.resetState()
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
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE6E7F0))
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Log In",
                    color = Color(0xFF0F115F),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = viewModel.emailInput,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("Email") },
                    shape = RoundedCornerShape(50),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = viewModel.passwordInput,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text("Password") },
                    shape = RoundedCornerShape(50),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text("Don't have an account?")
                Text("Sign Up", color = Color(0xFF0F115F), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.login()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F115F),
                        contentColor = Color.White
                    ),
                    enabled = loginState !is AuthUiState.Loading
                ) {
                    if (loginState is AuthUiState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Log In")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewUserLoginView() {
    //UserLoginView()
}