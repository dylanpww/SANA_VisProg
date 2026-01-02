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
import com.example.sana_visprog.model.Category
import com.example.sana_visprog.model.Province
import com.example.sana_visprog.repository.CategoryRepository
import com.example.sana_visprog.repository.DestinationRepository
import com.example.sana_visprog.repository.ProvinceRepository
import com.example.sana_visprog.utils.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class CreateDestinationViewModel(
    private val destinationRepository: DestinationRepository,
    private val categoryRepository: CategoryRepository,
    private val provinceRepository: ProvinceRepository
) : ViewModel() {

    var name = mutableStateOf("")
    var location = mutableStateOf("")
    var description = mutableStateOf("")
    var rating = 4.0f
    var categories = mutableStateOf<List<Category>>(emptyList())
    var provinces = mutableStateOf<List<Province>>(emptyList())
    var selectedCategory = mutableStateOf<Category?>(null)
    var selectedProvince = mutableStateOf<Province?>(null)
    var image1Uri = mutableStateOf<Uri?>(null)
    var image2Uri = mutableStateOf<Uri?>(null)

    var isLoading = mutableStateOf(false)

    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            try {
                categories.value = categoryRepository.getCategories()
                provinces.value = provinceRepository.getProvinces()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createDestination(context: Context, onSuccess: () -> Unit) {
        if (name.value.isEmpty() || location.value.isEmpty() || image1Uri.value == null ||
            selectedCategory.value == null || selectedProvince.value == null) {
            Toast.makeText(context, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val url1 = uploadImage(context, image1Uri.value!!) ?: throw Exception("Gagal upload gambar 1")

                var url2 = ""
                if (image2Uri.value != null) {
                    url2 = uploadImage(context, image2Uri.value!!) ?: ""
                }

                val request = CreateDestinationRequest(
                    name = name.value,
                    description = description.value,
                    location = location.value,
                    rating = rating,
                    pictureUrl = url1,
                    pictureUrl2 = url2,
                    categoryId = selectedCategory.value!!.id,
                    provinceId = selectedProvince.value!!.id
                )

                val success = destinationRepository.createDestination(request)
                if (success) {
                    Toast.makeText(context, "Berhasil!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                isLoading.value = false
            }
        }
    }

    private suspend fun uploadImage(context: Context, uri: Uri): String? {
        return try {
            val file = uriToFile(uri, context)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestImageFile)
            destinationRepository.uploadImage(multipartBody)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SANAVisProgApplication)
                CreateDestinationViewModel(
                    destinationRepository = app.container.destinationRepository,
                    categoryRepository = app.container.categoryRepository,
                    provinceRepository = app.container.provinceRepository
                )
            }
        }
    }
}