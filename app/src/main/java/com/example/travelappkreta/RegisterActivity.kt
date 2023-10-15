package com.example.travelappkreta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelappkreta.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (isUserValid()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun isUserValid(): Boolean {
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()
        val password = binding.etPassword.text.toString()

        if (isValidDateOfBirth(phone)) {
            saveUserData(username, email, phone, password)
            return true
        }

        Toast.makeText(this, "Invalid input. Please check your details.", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun isValidDateOfBirth(dateOfBirth: String): Boolean {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val birthDate = sdf.parse(dateOfBirth)
            val currentDate = Calendar.getInstance().time

            val diffInMillis = currentDate.time - birthDate.time
            val age = diffInMillis / (1000L * 60 * 60 * 24 * 365)

            return age >= 15
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun saveUserData(username: String, email: String, phone: String, password: String) {
        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("email", email)
        editor.putString("phone", phone)
        editor.putString("password", password)
        editor.apply()
    }

    fun goToLoginActivity(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
