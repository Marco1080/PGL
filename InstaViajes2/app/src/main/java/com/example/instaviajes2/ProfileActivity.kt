package com.example.instaviajes2

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private lateinit var descriptionInput: EditText
    private val SPEECH_REQUEST_CODE = 1
    private lateinit var dbHelper: DataBaseHelper
    private var originalUsername: String? = null
    private var originalEmail: String? = null
    private var originalPhone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        dbHelper = DataBaseHelper(this)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val extraImage = findViewById<ImageView>(R.id.extraImage)

        val username = intent.getStringExtra("username")

        if (username != null) {
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM Users WHERE username = ?", arrayOf(username))

            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val retrievedUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))

                println("Usuario encontrado:")
                println("ID: $id")
                println("Username: $retrievedUsername")
                println("Password: $password")
                println("Email: $email")
                println("Phone: $phone")

                originalUsername = retrievedUsername
                originalEmail = email
                originalPhone = phone

                usernameInput.setText(retrievedUsername)
                emailInput.setText(email)
                phoneInput.setText(phone)
            } else {
                println("Usuario no encontrado.")
            }
            cursor.close()
        } else {
            println("No se recibió username.")
        }

        saveButton.setOnClickListener {
            val updatedUsername = usernameInput.text.toString()
            val updatedEmail = emailInput.text.toString()
            val updatedPhone = phoneInput.text.toString()

            if (updatedUsername != originalUsername || updatedEmail != originalEmail || updatedPhone != originalPhone) {
                val updated = updateUser(originalUsername ?: "", updatedUsername, updatedEmail, updatedPhone)
                if (updated) {
                    Toast.makeText(this, "Perfil actualizado correctamente.", Toast.LENGTH_SHORT).show()
                    println("Usuario actualizado en la base de datos:")
                    println("Nuevo Username: $updatedUsername")
                    println("Nuevo Email: $updatedEmail")
                    println("Nuevo Phone: $updatedPhone")

                    val intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("username", updatedUsername)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar perfil.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No se detectaron cambios en los datos.", Toast.LENGTH_SHORT).show()
            }
        }

        extraImage.setOnClickListener {
            startSpeechToText()
        }
    }

    private fun updateUser(oldUsername: String, newUsername: String, email: String, phone: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues()

        if (oldUsername != newUsername) {
            values.put("username", newUsername)
        }
        values.put("email", email)
        values.put("phone", phone)

        val rowsUpdated = db.update("Users", values, "username = ?", arrayOf(oldUsername))
        return rowsUpdated > 0
    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora para llenar la descripción")
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(this, "El reconocimiento de voz no está disponible", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Método que recibe el resultado del reconocimiento de voz
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result?.let {
                descriptionInput.setText(it[0])
            }
        }
    }
}
