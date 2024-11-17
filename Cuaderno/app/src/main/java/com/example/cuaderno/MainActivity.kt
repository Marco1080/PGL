package com.example.cuaderno

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerColorVertical = findViewById<Spinner>(R.id.spinnerColorVertical)
        val items1 = listOf("Rojo", "Verde", "Azul")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColorVertical.adapter = adapter1

        val spinnerGrosorVertical = findViewById<Spinner>(R.id.spinnerGrosorVertical)
        val items2 = listOf("1", "3", "5")
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrosorVertical.adapter = adapter2

        val spinnerColorHorizontal = findViewById<Spinner>(R.id.spinnerColorHorizontal)
        val items3 = listOf("Rojo", "Verde", "Azul")
        val adapter3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items3)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColorHorizontal.adapter = adapter3

        val spinnerGrosorHorizontal = findViewById<Spinner>(R.id.spinnerGrosorHorizontal)
        val items4 = listOf("1", "3", "5")
        val adapter4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, items4)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrosorHorizontal.adapter = adapter4

        val buttonDraw = findViewById<Button>(R.id.button) // ID del botón en XML
        buttonDraw.setOnClickListener {
            val colorVertical = spinnerColorVertical.selectedItem.toString()
            val grosorVertical = spinnerGrosorVertical.selectedItem.toString()
            val colorHorizontal = spinnerColorHorizontal.selectedItem.toString()
            val grosorHorizontal = spinnerGrosorHorizontal.selectedItem.toString()

            val intent = Intent(this, DibujoActivity::class.java).apply {
                putExtra("COLOR_VERTICAL", colorVertical)
                putExtra("GROSOR_VERTICAL", grosorVertical)
                putExtra("COLOR_HORIZONTAL", colorHorizontal)
                putExtra("GROSOR_HORIZONTAL", grosorHorizontal)
            }
            startActivity(intent)
        }
    }
}
