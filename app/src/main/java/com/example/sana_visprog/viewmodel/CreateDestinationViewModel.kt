package com.example.sana_visprog.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sana_visprog.SANAVisProgApplication
import com.example.sana_visprog.dto.Destination.CreateDestinationRequest
import com.example.sana_visprog.repository.DestinationRepository
import com.example.sana_visprog.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CreateDestinationViewModel(
    private val repository: DestinationRepository
) : ViewModel() {

    // State untuk Input Form
    var name = mutableStateOf("")
    var city = mutableStateOf("")
    var country = mutableStateOf("")
    var description = mutableStateOf("")
    var imageUri = mutableStateOf<Uri?>(null)

    // State Loading
    var isLoading = mutableStateOf(false)

    fun createDestination(context: Context, onSuccess: () -> Unit) {
        if (name.value.isEmpty() || city.value.isEmpty() || imageUri.value == null) {
            Toast.makeText(context, "Nama, Kota, dan Gambar wajib diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val file = uriToFile(imageUri.value!!, context)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestImageFile)

                val uploadedUrl = repository.uploadImage(multipartBody)

                if (uploadedUrl != null) {
                    val request = CreateDestinationRequest(
                        name = name.value,
                        description = description.value,
                        location = "${city.value}, ${country.value}",
                        rating = 4.5f,
                        pictureUrl = uploadedUrl,
                        pictureUrl2 = "",
                        categoryId = 1,
                        provinceId = 1
                    )

                    val success = repository.createDestination(request)
                    if (success) {
                        Toast.makeText(context, "Berhasil membuat destinasi!", Toast.LENGTH_SHORT).show()
                        onSuccess()
                    } else {
                        Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Gagal upload gambar", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                CreateDestinationViewModel(app.container.destinationRepository)
            }
        }
    }
}