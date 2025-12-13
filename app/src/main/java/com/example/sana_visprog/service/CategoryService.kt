package com.example.sana_visprog.service

import retrofit2.http.*

interface CategoryService {
    // Category endpoints
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

data class CategoryRequest(
    val name: String
)