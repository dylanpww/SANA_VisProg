package com.example.sana_visprog.view.destination

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sana_visprog.model.Destination
import androidx.compose.ui.platform.LocalInspectionMode
import com.example.sana_visprog.view.plan.poppins

@Composable
fun DestinationCard(
    destination: Destination,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0XFF0F115F)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            if (LocalInspectionMode.current) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                )
            } else {
                AsyncImage(
                    model = destination.pictureUrl,
                    contentDescription = destination.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = destination.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = poppins,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = destination.description,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp,
                fontFamily = poppins,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
