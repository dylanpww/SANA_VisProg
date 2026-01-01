package com.example.sana_visprog.model

import com.google.gson.annotations.SerializedName

data class WebResponse<T>(
    @SerializedName("data")
    val data: T,

    @SerializedName("errors")
    val errors: String? = null
)

data class UploadResponse(
    @SerializedName("url")
    val url: String
)