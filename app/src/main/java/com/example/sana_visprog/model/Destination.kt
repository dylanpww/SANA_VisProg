package com.example.sana_visprog.model

data class Destination(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val rating: Float,
    val pictureUrl: String,
    val pictureUrl2: String,
    val categoryId: Int,
    val provinceId: Int
)