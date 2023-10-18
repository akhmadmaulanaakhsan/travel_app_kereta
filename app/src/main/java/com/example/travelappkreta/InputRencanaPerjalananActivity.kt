package com.example.travelappkreta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class PaketTambahan(val nama: String, val deskripsi: String)

class InputRencanaPerjalananActivity : AppCompatActivity() {

    private val stasiunList = listOf("Stasiun Jakarta", "Stasiun Bandung", "Stasiun Pekalongan", "Stasiun Semarang", "Stasiun Blitar", "Stasiun Surabaya")
    private val kelasKeretaList = listOf("Ekonomi", "Bisnis", "Eksekutif")
    private val paketTambahanList = listOf(
        PaketTambahan("Paket 1", "tambah makan siang"),
        PaketTambahan("Paket 2", "duduk di pinggir jendela"),
        PaketTambahan("Paket 3", "bantuan penanganan bagasi"),
        PaketTambahan("Paket 4", "wifi dengan kecepatan stabil"),
        PaketTambahan("Paket 5", "bantal leher, selimut hangat, dan penutup mata"),
        PaketTambahan("Paket 6", "layanan spa kecil seperti pijat atau perawatan wajah di dalam kereta"),
        PaketTambahan("Paket 7", "merchandise eksklusif kaos dan topi")
    )

    private val hargaKelasKeretaList = listOf(100000, 180000, 250000)
    private val hargaPaketTambahanList = listOf(30000, 30000, 40000, 20000, 40000, 50000, 90000)

    private lateinit var spinnerAsal: Spinner
    private lateinit var spinnerTujuan: Spinner
    private lateinit var spinnerClassKereta: Spinner
    private lateinit var horizontalScrollViewPaket: HorizontalScrollView
    private lateinit var llToggleButtons: LinearLayout
    private lateinit var textViewDetailPaket: TextView
    private lateinit var textViewHarga: TextView
    private lateinit var btnPesan: Button
    private lateinit var datePicker: DatePicker

    private var totalHarga: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerAsal = findViewById(R.id.spinnerAsal)
        spinnerTujuan = findViewById(R.id.spinnerTujuan)
        spinnerClassKereta = findViewById(R.id.spinnerClassKereta)
        horizontalScrollViewPaket = findViewById(R.id.horizontalScrollViewPaket)
        llToggleButtons = findViewById(R.id.llToggleButtons)
        textViewDetailPaket = findViewById(R.id.textViewDetailPaket)
        textViewHarga = findViewById(R.id.textViewHarga)
        btnPesan = findViewById(R.id.btnPesan)
        datePicker = findViewById(R.id.datePicker)

        // Setup Adapter untuk Spinner Asal, Tujuan, dan Kelas Kereta
        setupSpinner(spinnerAsal, stasiunList)
        setupSpinner(spinnerTujuan, stasiunList)
        setupSpinner(spinnerClassKereta, kelasKeretaList)

        // Setup Toggle Buttons
        setupToggleButtons(llToggleButtons, paketTambahanList)

        // Setup Listener untuk perubahan pada Spinner dan Toggle Buttons
        setupListeners()

        // Setup Button Pesan
        btnPesan.setOnClickListener {
            // Mendapatkan data yang ingin Anda kirim
            val selectedAsal = spinnerAsal.selectedItem.toString()
            val selectedTujuan = spinnerTujuan.selectedItem.toString()
            val selectedClassKereta = spinnerClassKereta.selectedItem.toString()
            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"

            saveTravelData(selectedDate, selectedAsal, selectedTujuan, textViewDetailPaket.text.toString())

            // Mengirim data ke DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java).apply {
                putExtra("ASAL", selectedAsal)
                putExtra("TUJUAN", selectedTujuan)
                putExtra("KELAS_KERETA", selectedClassKereta)
                putExtra("SELECTED_DATE", selectedDate)
                putExtra("TOTAL_HARGA", totalHarga)
            }

            // Memulai aktivitas DashboardActivity dengan membawa data
            startActivity(intent)
        }
    }

    fun saveTravelData(selectedDate: String, origin: String, destination: String, packages: String) {
        val sharedPreferences = getSharedPreferences("TravelData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("SELECTED_DATE_$selectedDate", selectedDate)
        editor.putString("ORIGIN_$selectedDate", origin)
        editor.putString("DESTINATION_$selectedDate", destination)
        editor.putString("PACKAGES_$selectedDate", packages)

        editor.apply()
    }

    private fun setupSpinner(spinner: Spinner, data: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun setupToggleButtons(group: LinearLayout, data: List<PaketTambahan>) {
        for (i in data.indices) {
            val toggleButton = ToggleButton(this)
            toggleButton.text = data[i].nama
            toggleButton.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            group.addView(toggleButton)
        }
    }

    private fun setupListeners() {
        spinnerAsal.onItemSelectedListener = SpinnerItemSelectedListener()
        spinnerTujuan.onItemSelectedListener = SpinnerItemSelectedListener()
        spinnerClassKereta.onItemSelectedListener = SpinnerItemSelectedListener()

        for (i in 0 until llToggleButtons.childCount) {
            val toggleButton = llToggleButtons.getChildAt(i) as ToggleButton
            toggleButton.setOnCheckedChangeListener { _, isChecked ->
                updateTotalHarga()
                updateDetailPaket()
            }
        }
    }

    private fun updateTotalHarga() {
        totalHarga = 0

        // Harga berdasarkan kelas kereta
        totalHarga += hargaKelasKeretaList[spinnerClassKereta.selectedItemPosition]

        // Harga berdasarkan paket tambahan yang dipilih
        for (i in 0 until llToggleButtons.childCount) {
            val toggleButton = llToggleButtons.getChildAt(i) as ToggleButton
            if (toggleButton.isChecked) {
                totalHarga += hargaPaketTambahanList[i]
            }
        }

        // Tampilkan total harga
        textViewHarga.text = "Harga: $totalHarga"
    }

    private fun updateDetailPaket() {
        val selectedPakets = mutableListOf<String>()

        // Tambahkan paket tambahan yang dipilih ke dalam list
        for (i in 0 until llToggleButtons.childCount) {
            val toggleButton = llToggleButtons.getChildAt(i) as ToggleButton
            if (toggleButton.isChecked) {
                selectedPakets.add(paketTambahanList[i].deskripsi)
            }
        }

        // Tampilkan detail paket di textViewDetailPaket
        textViewDetailPaket.text = "Detail Paket: ${selectedPakets.joinToString(", ")}"
    }

    inner class SpinnerItemSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            updateTotalHarga()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }
    }
}
