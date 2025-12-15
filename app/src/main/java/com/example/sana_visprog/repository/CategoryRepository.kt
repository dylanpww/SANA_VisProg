package com.example.sana_visprog.repository

import com.example.sana_visprog.model.ApiResponse
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.service.CategoryService
import com.example.sana_visprog.service.dto.CategoryRequest

class CategoryRepository(private val categoryService: CategoryService) {
    suspend fun getCategories(): ApiResponse<List<Category>> {
        return try {
            categoryService.getCategories()
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    suspend fun createCategory(name: String): ApiResponse<String> {
        val request = CategoryRequest(name = name)
        return try {
            categoryService.createCategory(request)
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    suspend fun updateCategory(categoryId: Int, name: String): ApiResponse<String> {
        val request = CategoryRequest(name = name)
        return try {
            categoryService.updateCategory(categoryId, request)
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