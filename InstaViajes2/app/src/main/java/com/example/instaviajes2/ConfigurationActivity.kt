package com.example.instaviajes2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout

class ConfigurationActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var switchHighContrast: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_configuration)

        mainLayout = findViewById(R.id.main)
        switchHighContrast = findViewById(R.id.switchHighContrast)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Cargar estado del modo nocturno
        val isNightMode = sharedPreferences.getBoolean("isNightMode", false)
        updateBackground(isNightMode)
        switchHighContrast.isChecked = isNightMode

        // Listener para el Switch
        switchHighContrast.setOnCheckedChangeListener { _, isChecked ->
            updateBackground(isChecked)
            saveNightModeState(isChecked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Actualiza el fondo según el estado del modo nocturno.
     */
    private fun updateBackground(isNightMode: Boolean) {
        if (isNightMode) {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.black, theme))
        } else {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.white, theme))
        }
    }

    /**
     * Guarda el estado del modo nocturno en SharedPreferences.
     */
    private fun saveNightModeState(isNightMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isNightMode", isNightMode)
        editor.apply()
    }
}
