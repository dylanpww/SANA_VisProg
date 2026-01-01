package com.example.sana_visprog.model

class Destination {
    data class WebResponse<T>(
        val data: T,
        val errors: String? = null
    )

    data class UploadResponse(
        val url: String
    )

    data class CreateDestinationRequest(
        val name: String,
        val description: String,
        val location: String,
        val rating: Float = 0.0f,
        val categoryId: Int,
        val provinceId: Int,
        val pictureUrl: String,   // URL Gambar 1
        val pictureUrl2: String   // URL Gambar 2
    )
}