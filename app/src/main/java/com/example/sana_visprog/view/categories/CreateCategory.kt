package com.example.sana_visprog.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.sana_visprog.utils.IconList
import com.example.sana_visprog.viewmodel.HomeViewModel

@Composable
fun CreateCategoryContent(
    name: String,
    selectedIcon: String,
    loading: Boolean,
    error: String?,
    onNameChange: (String) -> Unit,
    onIconSelect: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tambah Kategori",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Nama",
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = { Text(text = "Tambah Kategori") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Icon",
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(IconList.icons) { iconOption ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                if (selectedIcon == iconOption.name)
                                    Color(0xFF0D2C8A)
                                else
                                    Color(0xFFBFC7E6)
                            )
                            .clickable { onIconSelect(iconOption.name) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconOption.icon,
                            contentDescription = iconOption.name,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            if (loading || name.isBlank())
                                Color(0xFFD6DAEA)
                            else
                                Color(0xFFBFC7E6)
                        )
                        .clickable(
                            enabled = name.isNotBlank() && !loading
                        ) {
                            onSave()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (loading) "Menyimpan..." else "Simpan",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color(0xFF0D2C8A)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color(0xFFBFC7E6))
                        .clickable(enabled = !loading) {
                            onCancel()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color(0xFF0D2C8A)
                    )
                }
            }
        }
    }
}

@Composable
fun CreateCategoryView(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val name = remember { mutableStateOf("") }
    val selectedIcon = remember { mutableStateOf("") }

    LaunchedEffect(viewModel.createCategorySuccess.value) {
        if (viewModel.createCategorySuccess.value) {
            navController.popBackStack()
            viewModel.resetCreateState()
        }
    }

    CreateCategoryContent(
        name = name.value,
        selectedIcon = selectedIcon.value,
        loading = viewModel.createCategoryLoading.value,
        error = viewModel.createCategoryError.value,
        onNameChange = { name.value = it },
        onIconSelect = { selectedIcon.value = it },
        onSave = {
            viewModel.createCategory(name.value, selectedIcon.value)
        },
        onCancel = {
            viewModel.resetCreateState()
            navController.popBackStack()
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCreateCategoryView() {
    CreateCategoryContent(
        name = "Football Match",
        selectedIcon = "Sports",
        loading = false,
        error = null,
        onNameChange = {},
        onIconSelect = {},
        onSave = {},
        onCancel = {}
    )
}