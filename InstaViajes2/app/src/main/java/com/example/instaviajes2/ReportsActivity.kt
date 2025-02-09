package com.example.instaviajes2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportsActivity : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reports)

        dbHelper = DataBaseHelper(this)
        username = intent.getStringExtra("username") ?: "Guest"

        val issueInput = findViewById<EditText>(R.id.issueInput)
        val submitButton = findViewById<Button>(R.id.submitReportButton)

        submitButton.setOnClickListener {
            val issueText = issueInput.text.toString().trim()
            if (issueText.isNotEmpty()) {
                val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val success = dbHelper.insertReport(username, issueText, currentDate)
                if (success) {
                    Toast.makeText(this, "Reporte enviado con éxito", Toast.LENGTH_SHORT).show()
                    issueInput.text.clear()
                } else {
                    Toast.makeText(this, "Error al enviar el reporte", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, escribe un problema", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
