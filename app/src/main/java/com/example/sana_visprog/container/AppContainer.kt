package com.example.sana_visprog.container

import android.content.Context
import com.example.sana_visprog.repository.CategoryRepository
import com.example.sana_visprog.service.CategoryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(private val context: Context) {
    private val baseUrl = "http://192.168.0.9:3000/api/" //URL Vanness

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val categoryService: CategoryService = retrofit.create(CategoryService::class.java)

    val categoryRepository: CategoryRepository = CategoryRepository(categoryService)
}