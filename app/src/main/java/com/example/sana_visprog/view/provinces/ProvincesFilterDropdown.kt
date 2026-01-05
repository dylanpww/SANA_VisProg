package com.example.sana_visprog.view.provinces

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sana_visprog.model.Province

@Composable
fun ProvinceFilterDropdownContent(
    provinces: List<Province>,
    isExpanded: Boolean,
    selectedProvince: String?,
    onToggle: () -> Unit,
    onSelect: (String) -> Unit
) {
    val primaryDeepBlue = Color(0xFF0F115F)
    val lightBackground = Color(0xFFF5F7FA)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "Filter",
                    tint = if (selectedProvince == "Semua" || selectedProvince == null) Color.Gray else primaryDeepBlue
                )
            }
        }

        if (isExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.background(Color.White)
                ) {
                    item {
                        val isSelected = selectedProvince == "Semua" || selectedProvince == null

                        Text(
                            text = "Semua",
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) primaryDeepBlue else Color.White
                                )
                                .clickable { onSelect("Semua") }
                                .padding(vertical = 16.dp, horizontal = 20.dp),
                            color = if (isSelected) Color.White else primaryDeepBlue
                        )
                    }

                    items(provinces) { province ->
                        val isSelected = selectedProvince == province.name

                        Text(
                            text = province.name,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) primaryDeepBlue else Color.White
                                )
                                .clickable { onSelect(province.name) }
                                .padding(vertical = 16.dp, horizontal = 20.dp),
                            color = if (isSelected) Color.White else primaryDeepBlue
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProvinceFilterDropdownView(
    provinces: List<Province>,
    isExpanded: Boolean,
    selectedProvince: String?,
    onToggle: () -> Unit,
    onSelect: (String) -> Unit
) {
    ProvinceFilterDropdownContent(
        provinces = provinces,
        isExpanded = isExpanded,
        selectedProvince = selectedProvince,
        onToggle = onToggle,
        onSelect = onSelect
    )
}