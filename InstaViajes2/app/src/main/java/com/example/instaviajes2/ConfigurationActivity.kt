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

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        val isNightMode = sharedPreferences.getBoolean("isNightMode", false)
        updateBackground(isNightMode)
        switchHighContrast.isChecked = isNightMode

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

    private fun updateBackground(isNightMode: Boolean) {
        if (isNightMode) {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.black, theme))
        } else {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.white, theme))
        }
    }

    private fun saveNightModeState(isNightMode: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isNightMode", isNightMode)
        editor.apply()
    }
}
