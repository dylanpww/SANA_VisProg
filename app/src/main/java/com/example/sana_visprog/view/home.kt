package com.example.sana_visprog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.model.Destination
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.view.categories.CategorySection
import com.example.sana_visprog.view.destination.DestinationCard
import com.example.sana_visprog.view.provinces.ProvinceFilterDropdownView
import com.example.sana_visprog.viewmodel.HomeViewModel

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getCategories()
        viewModel.getProvinces()
        viewModel.getDestinations()
    }

    HomeContent(
        categories = viewModel.categories.value,
        categoriesLoading = viewModel.categoriesLoading.value,
        categoriesError = viewModel.categoriesError.value,
        provinces = viewModel.provinces.value,
        provincesLoading = viewModel.provincesLoading.value,
        provincesError = viewModel.provincesError.value,
        isProvinceExpanded = viewModel.isProvinceDropdownExpanded.value,
        selectedProvince = viewModel.selectedProvince.value,
        destinations = viewModel.destinations.value,

        onAddCategory = {
            viewModel.resetCreateState()
            navController.navigate(Screen.CREATE_CATEGORY.name)
        },
        onAddDestination = {
            navController.navigate(Screen.ADD_DESTINATION.name)
        },

        onCategoryClick = { category ->
            viewModel.resetUpdateState()
            viewModel.resetDeleteState()
            navController.navigate(
                "${Screen.CATEGORY_DETAIL.name}/${category.id}"
            )
        },

        onToggleProvince = {
            viewModel.isProvinceDropdownExpanded.value =
                !viewModel.isProvinceDropdownExpanded.value
        },

        onSelectProvince = { province ->
            viewModel.selectedProvince.value = province
            viewModel.isProvinceDropdownExpanded.value = false
        },

        onDestinationClick = { destination ->
            navController.navigate("${Screen.DESTINATION_DETAIL.name}/${destination.id}")
        }
    )
}

@Composable
fun HomeContent(
    categories: List<Category>,
    categoriesLoading: Boolean,
    categoriesError: String?,

    provinces: List<Province>,
    provincesLoading: Boolean,
    provincesError: String?,
    isProvinceExpanded: Boolean,
    selectedProvince: String?,

    destinations: List<Destination>,

    onAddCategory: () -> Unit,
    onAddDestination: () -> Unit,
    onCategoryClick: (Category) -> Unit,

    onToggleProvince: () -> Unit,
    onSelectProvince: (String) -> Unit,

    onDestinationClick: (Destination) -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(
                    color = Color(0xFF0F115F),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp)
        ) {

            if (categoriesLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            categoriesError?.let {
                Text(
                    text = "Categories Error: $it",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (!categoriesLoading && categoriesError == null) {
                CategorySection(
                    categories = categories,
                    onAddClick = onAddCategory,
                    onCategoryClick = onCategoryClick
                )
            }

            if (provincesLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            }

            provincesError?.let {
                Text(
                    text = "Provinces Error: $it",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (!provincesLoading && provincesError == null) {
                ProvinceFilterDropdownView(
                    provinces = provinces,
                    isExpanded = isProvinceExpanded,
                    selectedProvince = selectedProvince,
                    onToggle = onToggleProvince,
                    onSelect = { province ->
                        onSelectProvince(province.name)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Popular Destinations",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val chunkedDestinations = destinations.chunked(2)

            if (destinations.isEmpty()) {
                Text(
                    text = "Belum ada destinasi.",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Gray
                )
            }

            chunkedDestinations.forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { destination ->
                        Box(modifier = Modifier.weight(1f)) {
                            DestinationCard(
                                destination = destination,
                                onClick = { onDestinationClick(destination) }
                            )
                        }
                    }

                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        FloatingActionButton(
            onClick = onAddDestination,
            containerColor = Color(0xFF0F115F),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 90.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Destination"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    HomeContent(
        categories = listOf(
            Category(1, "Outdoor", "Park"),
            Category(2, "Indoor", "Museum"),
            Category(3, "Gaming", "Gaming")
        ),
        categoriesLoading = false,
        categoriesError = null,

        provinces = listOf(
            Province(1, "Jawa Timur"),
            Province(2, "Jawa Tengah"),
            Province(3, "Jawa Barat")
        ),
        provincesLoading = false,
        provincesError = null,
        isProvinceExpanded = false,
        selectedProvince = "Jawa Timur",

        destinations = listOf(
            Destination(1, "Bromo", "Gunung Indah", "Probolinggo", 4.8f, "", "", 1, 1),
            Destination(2, "Kawah Ijen", "Kawah Ijen Indah", "Banyuwangi", 4.9f, "", "", 1, 1),
            Destination(3, "Bromo", "Gunung Indah", "Probolinggo", 4.8f, "", "", 1, 1)
        ),

        onAddCategory = {},
        onAddDestination = {},
        onCategoryClick = {},
        onToggleProvince = {},
        onSelectProvince = {},
        onDestinationClick = {}
    )
}