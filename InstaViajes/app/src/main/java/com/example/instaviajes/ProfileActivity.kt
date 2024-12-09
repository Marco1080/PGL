package com.example.instaviajes

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar NumberPicker para la edad
        val numberPickerAge: NumberPicker = findViewById(R.id.numberPickerAge)
        numberPickerAge.minValue = 18
        numberPickerAge.maxValue = 100
        numberPickerAge.wrapSelectorWheel = true

        // Configurar Spinner para los países
        val spinnerCountry: Spinner = findViewById(R.id.spinnerCountry)
        val countries = arrayOf(
            "Spain", "France", "Germany", "United States", "Italy", "Australia", "Canada", "Mexico", "Argentina", "Brazil",
            "United Kingdom", "India", "China", "Japan", "South Korea", "Russia", "South Africa", "Egypt", "Saudi Arabia",
            "United Arab Emirates", "Turkey", "Chile", "Colombia", "Peru", "Venezuela", "Thailand", "Vietnam", "Malaysia",
            "Indonesia", "Singapore", "Philippines", "New Zealand", "Greece", "Portugal", "Belgium", "Netherlands",
            "Sweden", "Norway", "Denmark", "Finland", "Switzerland", "Austria", "Poland", "Ukraine", "Romania", "Bulgaria",
            "Czech Republic", "Hungary", "Croatia", "Slovakia", "Slovenia", "Serbia", "Montenegro", "Kosovo", "Albania",
            "North Macedonia", "Bosnia and Herzegovina", "Georgia", "Armenia", "Israel", "Jordan", "Lebanon", "Palestine",
            "Kuwait", "Bahrain", "Qatar", "Oman", "Yemen", "Iran", "Iraq", "Afghanistan", "Pakistan", "Nepal", "Sri Lanka",
            "Bangladesh", "Myanmar", "Laos", "Cambodia", "Bhutan", "Maldives", "Kazakhstan", "Uzbekistan", "Turkmenistan",
            "Kyrgyzstan", "Tajikistan", "Mongolia", "Cyprus", "Iceland", "Estonia", "Latvia", "Lithuania", "Belarus",
            "Poland", "Algeria", "Morocco", "Tunisia", "Libya", "Mauritania", "Senegal", "Ghana", "Nigeria", "Kenya",
            "Tanzania", "Uganda", "Zimbabwe", "Mozambique", "Angola", "Cameroon", "Congo", "Democratic Republic of Congo"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = adapter

        // Botón para guardar cambios
        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val pantallaMenu = Intent(this, MenuActivity::class.java)
            startActivity(pantallaMenu)
        }

        // Cambiar imagen de perfil
        val btnChangeProfileImage: Button = findViewById(R.id.btnChangeProfileImage)
        btnChangeProfileImage.setOnClickListener {
            // Acción para subir o cambiar la imagen
            Toast.makeText(this, "Change Profile Picture clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
