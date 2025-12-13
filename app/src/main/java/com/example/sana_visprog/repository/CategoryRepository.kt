package com.example.sana_visprog.repository

import com.example.sana_visprog.model.ApiResponse
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.service.CategoryService
import com.example.sana_visprog.service.dto.CategoryRequest

interface CategoryRepositoryInterface {
    suspend fun getCategories(): ApiResponse<List<Category>>

    suspend fun createCategory(request: CategoryRequest): ApiResponse<String>

    suspend fun updateCategory(
        categoryId: Int,
        request: CategoryRequest
    ): ApiResponse<String>

    suspend fun deleteCategory(categoryId: Int): ApiResponse<String>
}

class CategoryRepository(private val categoryService: CategoryService) : CategoryRepositoryInterface {
    override suspend fun getCategories(): ApiResponse<List<Category>> {
        return try {
            categoryService.getCategories()
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    override suspend fun createCategory(request: CategoryRequest): ApiResponse<String> {
        return try {
            categoryService.createCategory(request)
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    override suspend fun updateCategory(categoryId: Int, request: CategoryRequest): ApiResponse<String> {
        return try {
            categoryService.updateCategory(categoryId, request)
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }

    override suspend fun deleteCategory(categoryId: Int): ApiResponse<String> {
        return try {
            categoryService.deleteCategory(categoryId)
        } catch (e: Exception) {
            ApiResponse(error = e.message)
        }
    }
}