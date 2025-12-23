package com.example.sana_visprog.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.utils.IconList

@Composable
fun CategorySection(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(Color(0xFFE6E7F0))
    ) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 25.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(35.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(70.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF131677))
                            .clickable { onCategoryClick(category) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = IconList.getIconByName(category.icon),
                            contentDescription = category.name,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = category.name,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF131677),
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                }
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(70.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF131677))
                            .clickable { onAddClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Add",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF131677),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCategorySection() {
    val dummyCategories = listOf(
        Category(1, "Gaming", "Gaming"),
        Category(2, "Outdoor", "Hiking"),
        Category(3, "Beach", "Beach")
    )
    CategorySection(
        categories = dummyCategories,
        onAddClick = {},
        onCategoryClick = {}
    )
}