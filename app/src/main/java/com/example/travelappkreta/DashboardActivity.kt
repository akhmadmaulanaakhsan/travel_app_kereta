package com.example.travelappkreta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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
            // Di sini Anda dapat menangani perubahan tanggal yang dipilih oleh pengguna
            // Misalnya, tampilkan data perjalanan terakhir berdasarkan tanggal yang dipilih
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            updateLastTravelData(selectedDate)
        }


    }

    private fun updateLastTravelData(selectedDate: String) {
        // Di sini, Anda dapat mengambil dan menampilkan data perjalanan terakhir berdasarkan tanggal
        // Contoh: lastTravelData.text = "Data terakhir: [Tanggal, Asal, Tujuan, Paket]"
        // Gantilah dengan logika pengambilan data yang sesuai dengan proyek Anda.
    }
}
