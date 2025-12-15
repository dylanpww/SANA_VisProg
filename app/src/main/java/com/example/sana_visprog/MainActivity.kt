package com.example.sana_visprog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.sana_visprog.container.AppContainer
import com.example.sana_visprog.ui.theme.SANA_VisProgTheme
import com.example.sana_visprog.view.user.UserLoginView
import com.example.sana_visprog.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = AppContainer(applicationContext)
        val loginViewModel = LoginViewModel(appContainer.authRepository)
        setContent {
            SANA_VisProgTheme {
//                Navigation()
                UserLoginView(viewModel = loginViewModel)
            }
        }
    }
}