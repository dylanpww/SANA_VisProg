package com.example.sana_visprog.service

import com.example.sana_visprog.dto.Categories.CategoriesRequest
import com.example.sana_visprog.dto.Categories.CategoriesResponse
import com.example.sana_visprog.dto.Categories.getAllCategoriesResponse
import retrofit2.http.*

interface CategoryService {
    @GET("categories")
    suspend fun getCategories(): getAllCategoriesResponse

    @GET("categories/{categoryId}")
    suspend fun getCategoriesById(@Path("categoryId") categoryId: Int): CategoriesResponse

    @POST("categories")
    suspend fun createCategory(@Body request: CategoriesRequest)

    @PUT("categories/{categoryId}")
    suspend fun updateCategory(@Path("categoryId") categoryId: Int, @Body request: CategoriesRequest)

    @DELETE("categories/{categoryId}")
    suspend fun deleteCategory(@Path("categoryId") categoryId: Int)
}