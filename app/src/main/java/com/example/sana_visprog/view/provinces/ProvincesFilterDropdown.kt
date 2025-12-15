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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onToggle) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = "Filter",
                    tint = Color(0xFF162D82)
                )
            }
        }

        if (isExpanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 15.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.background(Color(0xFFEBF0FF))
                ) {
                    items(provinces) { province ->
                        Text(
                            text = province.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (selectedProvince == province.name)
                                        Color(0xFFB0CEFF)
                                    else
                                        Color(0xFFEBF0FF)
                                )
                                .clickable { onSelect(province.name) }
                                .padding(15.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProvinceFilterDropdownView(
    provinces: List<Province>
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf<String?>(null) }

    ProvinceFilterDropdownContent(
        provinces = provinces,
        isExpanded = isExpanded,
        selectedProvince = selectedProvince,
        onToggle = { isExpanded = !isExpanded },
        onSelect = { province ->
            selectedProvince = province
            isExpanded = false
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewProvinceFilterDropdown() {
    val provinces = listOf(
        Province(1, "Jawa Timur"),
        Province(2, "Jawa Tengah"),
        Province(3, "Jawa Barat"),
        Province(4, "Jawa Utara"),
        Province(5, "Jawa Selatan")
    )

    ProvinceFilterDropdownContent(
        provinces = provinces,
        isExpanded = true,
        selectedProvince = "Jawa Timur",
        onToggle = {},
        onSelect = {}
    )
}