package com.example.sana_visprog.viewmodel

import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.repository.CategoryRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.repository.ProvinceRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val provinceRepository: ProvinceRepository
) : ViewModel() {
    val categories = mutableStateOf<List<Category>>(emptyList())
    val categoriesLoading = mutableStateOf(false)
    val categoriesError = mutableStateOf<String?>(null)

    val createCategoryLoading = mutableStateOf(false)
    val createCategoryError = mutableStateOf<String?>(null)
    val createCategorySuccess = mutableStateOf(false)

    val updateCategoryLoading = mutableStateOf(false)
    val updateCategoryError = mutableStateOf<String?>(null)
    val updateCategorySuccess = mutableStateOf(false)

    val deleteCategoryLoading = mutableStateOf(false)
    val deleteCategoryError = mutableStateOf<String?>(null)
    val deleteCategorySuccess = mutableStateOf(false)

    fun getCategories() {
        categoriesLoading.value = true
        categoriesError.value = null

        viewModelScope.launch {
            try {
                categories.value = categoryRepository.getCategories()
            } catch (e: Exception) {
                categoriesError.value = e.message
            } finally {
                categoriesLoading.value = false
            }
        }
    }

    fun createCategory(name: String) {
        createCategoryLoading.value = true
        createCategoryError.value = null
        createCategorySuccess.value = false

        viewModelScope.launch {
            try {
                val category = categoryRepository.createCategory(name)
                createCategorySuccess.value = true
                getCategories()
            } catch (e: Exception) {
                createCategoryError.value = e.message
            } finally {
                createCategoryLoading.value = false
            }
        }
    }

    fun updateCategory(categoryId: Int, name: String) {
        updateCategoryLoading.value = true
        updateCategoryError.value = null
        updateCategorySuccess.value = false

        viewModelScope.launch {
            try {
                categoryRepository.updateCategory(categoryId, name)
                updateCategorySuccess.value = true
                getCategories()
            } catch (e: Exception) {
                updateCategoryError.value = e.message
            } finally {
                updateCategoryLoading.value = false
            }
        }
    }

    fun deleteCategory(categoryId: Int) {
        deleteCategoryLoading.value = true
        deleteCategoryError.value = null
        deleteCategorySuccess.value = false

        viewModelScope.launch {
            try {
                categoryRepository.deleteCategory(categoryId)
                deleteCategorySuccess.value = true
                getCategories()
            } catch (e: Exception) {
                deleteCategoryError.value = e.message
            } finally {
                deleteCategoryLoading.value = false
            }
        }
    }

    fun resetCreateState() {
        createCategoryLoading.value = false
        createCategoryError.value = null
        createCategorySuccess.value = false
    }

    fun resetUpdateState() {
        updateCategoryLoading.value = false
        updateCategoryError.value = null
        updateCategorySuccess.value = false
    }

    fun resetDeleteState() {
        deleteCategoryLoading.value = false
        deleteCategoryError.value = null
        deleteCategorySuccess.value = false
    }

    val provinces = mutableStateOf<List<Province>>(emptyList())
    val provincesLoading = mutableStateOf(false)
    val provincesError = mutableStateOf<String?>(null)

    val isProvinceDropdownExpanded = mutableStateOf(false)
    val selectedProvince = mutableStateOf<String?>(null)

    fun getProvinces() {
        provincesLoading.value = true
        provincesError.value = null

        viewModelScope.launch {
            try {
                provinces.value = provinceRepository.getProvinces()
            } catch (e: Exception) {
                provincesError.value = e.message
            } finally {
                provincesLoading.value = false
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as SANAVisProgApplication)

                HomeViewModel(
                    categoryRepository = application.container.categoryRepository,
                    provinceRepository = application.container.provinceRepository
                )
            }
        }
    }
}