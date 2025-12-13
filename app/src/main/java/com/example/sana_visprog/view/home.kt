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
import com.example.sana_visprog.routing.Screen
import com.example.sana_visprog.view.categories.CategorySection
import com.example.sana_visprog.viewmodel.HomeViewModel

@Composable
fun HomeView(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    HomeContent(
        categories = viewModel.categories.value,
        categoriesLoading = viewModel.categoriesLoading.value,
        categoriesError = viewModel.categoriesError.value,

        onAddCategory = {
            viewModel.resetCreateState()
            navController.navigate(Screen.CREATE_CATEGORY.name)
        },

        onCategoryClick = { category ->
            viewModel.resetUpdateState()
            viewModel.resetDeleteState()
            navController.navigate(
                "${Screen.CATEGORY_DETAIL.name}/${category.id}/${category.name}"
            )
        }
    )
}

@Composable
fun HomeContent(
    categories: List<Category>,
    categoriesLoading: Boolean,
    categoriesError: String?,

    onAddCategory: () -> Unit,
    onCategoryClick: (Category) -> Unit
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    HomeContent(
        categories = listOf(
            Category(id = 1, name = "Outdoor"),
            Category(id = 2, name = "Indoor"),
            Category(id = 3, name = "Gaming")
        ),
        categoriesLoading = false,
        categoriesError = null,
        onAddCategory = {},
        onCategoryClick = {}
    )
}