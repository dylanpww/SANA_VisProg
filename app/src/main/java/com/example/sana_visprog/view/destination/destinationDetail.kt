package com.example.sana_visprog.view.destination

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.sana_visprog.model.Destination


val dummyDestinations = listOf(
    Destination(
        id = 1,
        name = "Mount Bromo",
        description = "Gunung Bromo adalah gunung berapi somma aktif dan bagian dari pegunungan Tengger, di Jawa Timur, Indonesia. Dengan ketinggian 2.329 meter, gunung ini bukanlah puncak tertinggi dari pegunungan tersebut, namun merupakan yang paling terkenal.",
        location = "Probolinggo, Jawa Timur",
        rating = 4.8f,
        pictureUrl = "https://images.unsplash.com/photo-1588668214407-6ea4e6d8c55e?q=80&w=1000&auto=format&fit=crop",
        pictureUrl2 = "",
        categoryId = 1,
        provinceId = 1
    ),
    Destination(
        id = 2,
        name = "Kawah Ijen",
        description = "Kawah Ijen adalah danau kawah asam berwarna biru kehijauan yang terletak di kompleks gunung berapi Ijen.",
        location = "Banyuwangi, Jawa Timur",
        rating = 4.9f,
        pictureUrl = "https://images.unsplash.com/photo-1518182170546-07b6bc527f3e?q=80&w=1000&auto=format&fit=crop",
        pictureUrl2 = "",
        categoryId = 1,
        provinceId = 1
    )
)
@Composable
fun DestinationDetailView(
    navController: NavController,
    destinationId: Int
) {
    val destination = dummyDestinations.find { it.id == destinationId } ?: dummyDestinations[0]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF0F115F))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = destination.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = destination.location,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            AsyncImage(
                model = destination.pictureUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- RATING ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${destination.rating}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = destination.description,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(100.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { /* Todo: Go to Events */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0XFFB0B6D1)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "See Events",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDestinationCard() {
    DestinationCard(dummyDestinations[0])
}

@Preview(showBackground = true)
@Composable
fun PreviewDetail() {
    DestinationDetailView(
        navController = rememberNavController(),
        destinationId = 1
    )
}