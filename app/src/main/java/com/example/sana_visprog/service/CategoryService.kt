package com.example.sana_visprog.service

import com.example.sana_visprog.model.ApiResponse
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.service.dto.CategoryRequest
import retrofit2.http.*

interface CategoryService {
    @GET("categories")
    suspend fun getCategories(): ApiResponse<List<Category>>

    @POST("categories")
    suspend fun createCategory(@Body request: CategoryRequest): ApiResponse<String>

    @PUT("categories/{categoryId}")
    suspend fun updateCategory(
        @Path("categoryId") categoryId: Int,
        @Body request: CategoryRequest
    ): ApiResponse<String>

    @DELETE("categories/{categoryId}")
    suspend fun deleteCategory(@Path("categoryId") categoryId: Int): ApiResponse<String>
}