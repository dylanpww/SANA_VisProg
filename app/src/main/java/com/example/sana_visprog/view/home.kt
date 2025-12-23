package com.example.sana_visprog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.view.categories.CategorySection
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

        onAddCategory = {
            viewModel.resetCreateState()
            navController.navigate(Screen.CREATE_CATEGORY.name)
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

    onAddCategory: () -> Unit,
    onCategoryClick: (Category) -> Unit,

    onToggleProvince: () -> Unit,
    onSelectProvince: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
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
            Province(3, "Jawa Barat"),
            Province(4, "Jawa Utara"),
            Province(5, "Jawa Selatan")
        ),
        provincesLoading = false,
        provincesError = null,
        isProvinceExpanded = true,
        selectedProvince = "Jawa Timur",

        onAddCategory = {},
        onCategoryClick = {},
        onToggleProvince = {},
        onSelectProvince = {}
    )
}