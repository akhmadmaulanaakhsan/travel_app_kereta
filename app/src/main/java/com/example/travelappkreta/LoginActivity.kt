package com.example.travelappkreta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelappkreta.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if (isLoginValid()) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }
    private fun isLoginValid(): Boolean {
        val usernameInput = binding.etUsername.text.toString()
        val passwordInput = binding.etPassword.text.toString()

        // Dapatkan data pengguna dari SharedPreferences
        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val savedUsername = sharedPref.getString("username", "")
        val savedPassword = sharedPref.getString("password", "")

        // Perform login validation
        if (usernameInput == savedUsername && passwordInput == savedPassword) {
            return true
        } else {
            Toast.makeText(this, "Invalid login credentials.", Toast.LENGTH_SHORT).show()
            return false
        }


        fun goToRegisterActivity(view: View) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
