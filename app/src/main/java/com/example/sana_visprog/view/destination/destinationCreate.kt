package com.example.sana_visprog.view.destination

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.viewmodel.CreateDestinationViewModel
val LightGreyBg = Color(0xFFF3F4F6)

@Composable
fun CreateDestinationView(
    navController: NavController,
    viewModel: CreateDestinationViewModel = viewModel(factory = CreateDestinationViewModel.Factory)
) {
    val context = LocalContext.current

    val pickImage1 = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { viewModel.image1Uri.value = it }
    val pickImage2 = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { viewModel.image2Uri.value = it }

    CreateDestinationContent(
        name = viewModel.name.value, onNameChange = { viewModel.name.value = it },
        location = viewModel.location.value, onLocationChange = { viewModel.location.value = it }, // HANYA LOCATION
        description = viewModel.description.value, onDescriptionChange = { viewModel.description.value = it },

        categories = viewModel.categories.value,
        selectedCategory = viewModel.selectedCategory.value,
        onCategorySelected = { viewModel.selectedCategory.value = it },

        provinces = viewModel.provinces.value,
        selectedProvince = viewModel.selectedProvince.value,
        onProvinceSelected = { viewModel.selectedProvince.value = it },

        image1Uri = viewModel.image1Uri.value,
        image2Uri = viewModel.image2Uri.value,
        isLoading = viewModel.isLoading.value,

        onPickImage1 = { pickImage1.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        onPickImage2 = { pickImage2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        onSave = {
            viewModel.createDestination(context) {
                navController.popBackStack()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDestinationContent(
    name: String, onNameChange: (String) -> Unit,
    location: String, onLocationChange: (String) -> Unit, // Ganti City+Country jadi Location
    description: String, onDescriptionChange: (String) -> Unit,

    categories: List<Category>, selectedCategory: Category?, onCategorySelected: (Category) -> Unit,
    provinces: List<Province>, selectedProvince: Province?, onProvinceSelected: (Province) -> Unit,

    image1Uri: Uri?, image2Uri: Uri?,
    isLoading: Boolean,
    onPickImage1: () -> Unit, onPickImage2: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        containerColor = LightGreyBg,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF0F115F), RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text("Kita Pergi ke SANA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("New Destination", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F115F), modifier = Modifier.padding(bottom = 24.dp))

            // --- FIELDS SESUAI DATABASE ---
            CustomTextField(label = "Destination name", value = name, onValueChange = onNameChange)
            Spacer(modifier = Modifier.height(16.dp))

            // Category & Province (Untuk ID)
            CustomDropdown(label = "Category", options = categories, selectedOption = selectedCategory, onOptionSelected = onCategorySelected, itemLabel = { it.name })
            Spacer(modifier = Modifier.height(16.dp))
            CustomDropdown(label = "Province", options = provinces, selectedOption = selectedProvince, onOptionSelected = onProvinceSelected, itemLabel = { it.name })
            Spacer(modifier = Modifier.height(16.dp))

            // Location (Satu Field Saja)
            CustomTextField(label = "Location", value = location, onValueChange = onLocationChange)
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(label = "Description", value = description, isSingleLine = false, onValueChange = onDescriptionChange)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Upload Pictures (Main & Side)", fontWeight = FontWeight.Bold, color = Color(0xFF0F115F), modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ImageUploadBox(uri = image1Uri, label = "Main", onClick = onPickImage1, modifier = Modifier.weight(1f).height(150.dp))
                ImageUploadBox(uri = image2Uri, label = "Side", onClick = onPickImage2, modifier = Modifier.weight(1f).height(150.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.width(150.dp).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFAAB0C5)),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color(0xFF0F115F))
                else Text("Save", color = Color(0xFF0F115F), fontWeight = FontWeight.Bold)
            }
        }
    }
}

// --- HELPER COMPONENTS (SAMA SEPERTI SEBELUMNYA) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomDropdown(label: String, options: List<T>, selectedOption: T?, onOptionSelected: (T) -> Unit, itemLabel: (T) -> String) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color(0xFF0F115F), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(6.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedOption?.let { itemLabel(it) } ?: "Select $label",
                onValueChange = {}, readOnly = true,
                trailingIcon = { Icon(if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown, null, tint = Color(0xFF0F115F)) },
                modifier = Modifier.fillMaxWidth().menuAnchor(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color(0xFFE8E8E8), focusedContainerColor = Color(0xFFE8E8E8), unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color(0xFF0F115F))
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(Color.White)) {
                options.forEach { option -> DropdownMenuItem(text = { Text(itemLabel(option)) }, onClick = { onOptionSelected(option); expanded = false }) }
            }
        }
    }
}

@Composable
fun ImageUploadBox(uri: Uri?, label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.clip(RoundedCornerShape(12.dp)).background(Color(0xFFE0E0E0)).clickable { onClick() }, contentAlignment = Alignment.Center) {
        if (uri != null) AsyncImage(model = uri, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        else Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.AddPhotoAlternate, null, tint = Color.Gray); Text(label, color = Color.Gray, fontSize = 12.sp) }
    }
}

@Composable
fun CustomTextField(label: String, value: String, isSingleLine: Boolean = true, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color(0xFF0F115F), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().then(if (!isSingleLine) Modifier.height(100.dp) else Modifier),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color(0xFFE8E8E8), focusedContainerColor = Color(0xFFE8E8E8), unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color(0xFF0F115F)),
            singleLine = isSingleLine
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSimple() {
    CreateDestinationContent("Bromo", {}, "Probolinggo, Jawa Timur, Indonesia", {}, "Keren", {}, emptyList(), null, {}, emptyList(), null, {}, null, null, false, {}, {}, {})
}