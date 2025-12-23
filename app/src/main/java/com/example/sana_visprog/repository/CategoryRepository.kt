package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.Categories.CategoriesRequest
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.service.CategoryService

class CategoryRepository(private val categoryService: CategoryService) {
    suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().data.map {
            Category(
                id = it.id,
                name = it.name,
                icon = it.icon
            )
        }
    }

    suspend fun createCategory(name: String, icon: String) {
        categoryService.createCategory(
            CategoriesRequest(name, icon)
        )
    }

    suspend fun updateCategory(categoryId: Int, name: String, icon: String) {
        categoryService.updateCategory(
            categoryId,
            CategoriesRequest(name, icon)
        )
    }

    suspend fun deleteCategory(categoryId: Int) {
        categoryService.deleteCategory(categoryId)
    }
}