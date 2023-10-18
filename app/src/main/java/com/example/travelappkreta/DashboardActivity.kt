package com.example.travelappkreta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelappkreta.InputRencanaPerjalananActivity
import com.example.travelappkreta.R

class DashboardActivity : AppCompatActivity() {

    private lateinit var btnTambahPesan: Button
    private lateinit var date_Picker: CalendarView
    private lateinit var textViewRencanaPerjalanan: TextView
    private lateinit var lastTravelData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Inisialisasi komponen
        btnTambahPesan = findViewById(R.id.btnTambahPesan)
        date_Picker = findViewById(R.id.date_Picker)
        textViewRencanaPerjalanan = findViewById(R.id.textViewRencanaPerjalanan)
        lastTravelData = findViewById(R.id.lastTravelData)

        // Set listener untuk tombol tambah pesan
        btnTambahPesan.setOnClickListener {
            val intent = Intent(this, InputRencanaPerjalananActivity::class.java)
            startActivity(intent)
        }

        // Set listener untuk CalendarView
        date_Picker.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            updateLastTravelData(selectedDate)
        }
    }

    private fun updateLastTravelData(selectedDate: String) {
        // Ambil data dari SharedPreferences
        val lastTravelInfo = getLastTravelData(selectedDate)

        // Tampilkan data terakhir di TextView
        lastTravelData.text = lastTravelInfo

        // Tampilkan Toast apakah terdapat rencana perjalanan pada tanggal yang dipilih
        val isTravelPlanExist = checkIfTravelPlanExist(selectedDate)
        val toastMessage = if (isTravelPlanExist) {
            "Terdapat rencana perjalanan pada tanggal $selectedDate"
        } else {
            "Tidak terdapat rencana perjalanan pada tanggal $selectedDate"
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
    }

    private fun getLastTravelData(selectedDate: String): String {
        val sharedPreferences = getSharedPreferences("TravelData", Context.MODE_PRIVATE)
        val date = sharedPreferences.getString("SELECTED_DATE_$selectedDate", "")
        val origin = sharedPreferences.getString("ORIGIN_$selectedDate", "")
        val destination = sharedPreferences.getString("DESTINATION_$selectedDate", "")
        val packages = sharedPreferences.getString("PACKAGES_$selectedDate", "")

        return "Data terakhir:\n" +
                "Tanggal: $date\n" +
                "Asal: $origin\n" +
                "Tujuan: $destination\n" +
                "Paket: $packages"
    }

    private fun checkIfTravelPlanExist(selectedDate: String): Boolean {

        val sharedPreferences = getSharedPreferences("TravelData", Context.MODE_PRIVATE)
        return sharedPreferences.contains("SELECTED_DATE_$selectedDate")
    }
}

