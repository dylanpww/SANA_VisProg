package com.example.sana_visprog.repository

import com.example.sana_visprog.dto.Categories.CategoriesRequest
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.service.CategoryService

class CategoryRepository(private val categoryService: CategoryService) {
    suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().data.map {
            Category(
                id = it.id,
                name = it.name
            )
        }
    }

    suspend fun getCategoriesById(categoryId: Int): Category {
        val response = categoryService.getCategoriesById(categoryId)
        val data = response.data

        return Category(
            id = data.id,
            name = data.name
        )
    }

    suspend fun createCategory(name: String) {
        categoryService.createCategory(
            CategoriesRequest(name)
        )
    }

    suspend fun updateCategory(categoryId: Int, name: String) {
        categoryService.updateCategory(
            categoryId,
            CategoriesRequest(name)
        )
    }

    suspend fun deleteCategory(categoryId: Int) {
        categoryService.deleteCategory(categoryId)
    }
}