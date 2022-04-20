package com.example.dummyapi

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dummyapi.databinding.ActivityProfileBinding
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import okhttp3.RequestBody.Companion.toRequestBody

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


        val username = 1
        val image = File(imageUri?.path.toString())







    }

    private var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            binding.ivProfil.setImageURI(imageUri)
            selectedImage = true
        }
    }


    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        launchSomeActivity.launch(intentGallery)
    }
}