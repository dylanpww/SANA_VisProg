package com.example.sana_visprog.repository

import com.example.sana_visprog.service.CategoryRequest
import com.example.sana_visprog.service.CategoryService

class CategoryRepository(private val categoryService: CategoryService) {

    suspend fun getCategories(): ApiResponse<List<Category>> {
        return try {
            categoryService.getCategories()
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    suspend fun createCategory(name: String): ApiResponse<String> {
        return try {
            categoryService.createCategory(CategoryRequest(name))
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    suspend fun updateCategory(categoryId: Int, name: String): ApiResponse<String> {
        return try {
            categoryService.updateCategory(categoryId, CategoryRequest(name))
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    suspend fun deleteCategory(categoryId: Int): ApiResponse<String> {
        return try {
            categoryService.deleteCategory(categoryId)
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }
}