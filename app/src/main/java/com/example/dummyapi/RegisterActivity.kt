package com.example.dummyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dummyapi.data.ApiClient
import com.example.dummyapi.data.LoginRequest
import com.example.dummyapi.data.RegisterRequest
import com.example.dummyapi.data.RegisterResponse
import com.example.dummyapi.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            btnRegis.setOnClickListener {
                val email = etEmail.text.toString()
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val registerRequest = RegisterRequest(email, password, username)
                postRegis(registerRequest)
            }
        }



    }

    private fun postRegis(request: RegisterRequest){
        ApiClient.instance.postRegister(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val code = response.code()
                if (code==201){
                    Toast.makeText(this@RegisterActivity, "register sukses", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }
        })
    }
}