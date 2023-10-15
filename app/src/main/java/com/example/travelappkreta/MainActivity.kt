package com.example.travelappkreta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.travelappkreta.databinding.ActivityWelcomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister1.setOnClickListener {
            Log.d("MainActivity", "Get Started button clicked")
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}