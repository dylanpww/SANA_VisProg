package com.example.sana_visprog.utils

import android.util.Base64
import org.json.JSONObject

object JwtUtils {
    fun decodeUsername(token: String): String {
        try {
            val split = token.split(".")
            if (split.size < 2) return ""

            val payloadBase64 = split[1]
            val decodedBytes = Base64.decode(payloadBase64, Base64.URL_SAFE)
            val decodedString = String(decodedBytes, Charsets.UTF_8)
            val jsonObject = JSONObject(decodedString)

            // Sesuaikan "username" dengan key yang ada di token backendmu
            // Kalau di tokenmu namanya "name", ganti jadi "name"
            return jsonObject.optString("username", "")

        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}