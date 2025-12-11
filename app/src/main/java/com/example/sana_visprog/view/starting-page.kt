package com.example.sana_visprog.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sana_visprog.R
import com.example.sana_visprog.routing.Screen
import kotlinx.coroutines.delay

val poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_semibold, FontWeight.SemiBold)
)

@Composable
fun StartingPage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFF0F115E)
            )
    ){
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "" ,
                modifier = Modifier
                    .size(110.dp)
            )
            Text(
                text = "SANA",
                color = Color.White,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp
            )
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            Text(
                text = "Temukan tujuanmu di ",
                color = Color.White,
                fontFamily = poppins,
                fontSize = 16.sp
            )
            Text(
                text = "SANA",
                color = Color.White,
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold

            )
        }

    }

    LaunchedEffect(Unit) {
        delay(3000)

        //popUpTo itu biar pas udah ke HOME ga ada tombol back lagi ke STARTING meskipun di back manual pake hp
        navController.navigate(Screen.HOME.name){
            popUpTo(Screen.STARTING.name){
                inclusive = true
            }
        }
    }
}

//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun StartingPagePreview() {
//    StartingPage(navController = NavHostController())
//}

