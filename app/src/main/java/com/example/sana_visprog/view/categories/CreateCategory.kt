package com.example.sana_visprog.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.sana_visprog.viewmodel.HomeViewModel

@Composable
fun CreateCategoryContent(
    name: String,
    loading: Boolean,
    error: String?,
    onNameChange: (String) -> Unit,
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
                text = "Add New Category",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Name",
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0D2C8A)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                placeholder = { Text(text = "Add New Category") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

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
                        text = if (loading) "Saving..." else "Save",
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

    LaunchedEffect(viewModel.createCategorySuccess.value) {
        if (viewModel.createCategorySuccess.value != null) {
            navController.popBackStack()
            viewModel.resetCreateState()
        }
    }

    CreateCategoryContent(
        name = name.value,
        loading = viewModel.createCategoryLoading.value,
        error = viewModel.createCategoryError.value,
        onNameChange = { name.value = it },
        onSave = {
            viewModel.createCategory(name.value)
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
        loading = false,
        error = null,
        onNameChange = {},
        onSave = {},
        onCancel = {}
    )
}