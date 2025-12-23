package com.example.sana_visprog.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun CategoryDetailContent(
    title: String,
    name: String,
    selectedIcon: String,
    loading: Boolean,
    error: String?,
    onNameChange: (String) -> Unit,
    onIconSelect: (String) -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F3F7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D2C8A),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Name",
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
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

            Spacer(modifier = Modifier.height(35.dp))

            error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color(0xFF0D2C8A)))

            Spacer(modifier = Modifier.height(20.dp))

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
                            onUpdate()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Update",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D2C8A),
                        fontSize = 16.sp
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
                            onDelete()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0D2C8A),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryDetailView(
    navController: NavController,
    categoryId: Int,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        if (viewModel.categories.value.isEmpty()) {
            viewModel.getCategories()
        }
    }

    val category = viewModel.categories.value.find { it.id == categoryId }

    var name by remember(category) { mutableStateOf(category?.name ?: "") }
    var selectedIcon by remember(category) { mutableStateOf(category?.icon ?: "Category") }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val loading =
        viewModel.updateCategoryLoading.value ||
                viewModel.deleteCategoryLoading.value

    LaunchedEffect(viewModel.updateCategorySuccess.value) {
        if (viewModel.updateCategorySuccess.value) {
            navController.popBackStack()
            viewModel.resetUpdateState()
        }
    }

    LaunchedEffect(viewModel.deleteCategorySuccess.value) {
        if (viewModel.deleteCategorySuccess.value) {
            navController.popBackStack()
            viewModel.resetDeleteState()
        }
    }

    if (category == null && viewModel.categoriesLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (category == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Category not found",
                color = Color.Red,
                fontSize = 18.sp
            )
        }
    } else {
        CategoryDetailContent(
            title = name,
            name = name,
            selectedIcon = selectedIcon,
            loading = loading,
            error = viewModel.updateCategoryError.value
                ?: viewModel.deleteCategoryError.value,
            onNameChange = { name = it },
            onIconSelect = { selectedIcon = it },
            onUpdate = {
                viewModel.updateCategory(categoryId, name, selectedIcon)
            },
            onDelete = {
                showDeleteDialog = true
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Delete Category") },
            text = { Text(text = "Are you sure you want to delete this category?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteCategory(categoryId)
                        showDeleteDialog = false
                    }
                ) {
                    Text(text = "Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCategoryDetailContent() {
    CategoryDetailContent(
        title = "Outdoor",
        name = "Outdoor",
        selectedIcon = "Hiking",
        loading = false,
        error = null,
        onNameChange = {},
        onIconSelect = {},
        onUpdate = {},
        onDelete = {}
    )
}