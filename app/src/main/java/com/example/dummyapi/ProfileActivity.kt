package com.example.dummyapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.dummyapi.data.ApiClient
import com.example.dummyapi.data.UserUpdateResponse
import com.example.dummyapi.databinding.ActivityProfileBinding
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private var selectedImage = false
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnUpload.setOnClickListener {
            openGallery()
        }

        val sharedPreference = getSharedPreferences("SHARED_FILE", Context.MODE_PRIVATE)

        val token = sharedPreference?.getString("token","")

        binding.btnUpdate.setOnClickListener {

            val imageFile = File(imageUri?.path.toString())
            val newEmail = binding.etEmail.text.toString()
            val newUsername = binding.etUsername.text.toString()

            val emailBody = newEmail.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val usernameBody = newUsername.toRequestBody("multipart/form-data".toMediaTypeOrNull())
//            val requestImage = imageFile.readBytes().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val requestImage = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val imageBody = MultipartBody.Part.createFormData("photo", imageFile.name,requestImage)

            ApiClient.instance.putUser("Bearer $token", usernameBody,emailBody,imageBody)
                .enqueue(object : Callback<UserUpdateResponse> {
                    override fun onResponse(
                        call: Call<UserUpdateResponse>,
                        response: Response<UserUpdateResponse>
                    ) {
                        Toast.makeText(this@ProfileActivity, "sukses", Toast.LENGTH_SHORT).show()
                        Glide.with(this@ProfileActivity).load(response.body()?.data?.photo)
                            .into(binding.ivNewProfile)
                    }

                    override fun onFailure(call: Call<UserUpdateResponse>, t: Throwable) {
                        Toast.makeText(this@ProfileActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                })



        }


    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            binding.ivProfil.setImageURI(imageUri)
//            Toast.makeText(this, imageUri?.path, Toast.LENGTH_SHORT).show()
            selectedImage = true
//            val imageFile = File(imageUri!!.path!!)
//            Toast.makeText(this, imageFile.absolutePath, Toast.LENGTH_SHORT).show()
        }
    }


    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        launchSomeActivity.launch(intentGallery)
    }
}