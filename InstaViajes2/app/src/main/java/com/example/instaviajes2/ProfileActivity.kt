package com.example.instaviajes2

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inicializar la base de datos
        dbHelper = DataBaseHelper(this)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val extraImage = findViewById<ImageView>(R.id.extraImage)

        // Recibir el Intent y extraer el "username"
        val username = intent.getStringExtra("username")

        // Realizar un select del usuario y mostrar cada columna por consola
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

                // Mostrar los datos en los EditTexts
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
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val description = descriptionInput.text.toString()

            // Mostrar mensaje de confirmación
            Toast.makeText(this, "Perfil actualizado.", Toast.LENGTH_SHORT).show()

            // Crear un Intent para redirigir al MenuActivity
            val intent = Intent(this, MenuActivity::class.java)

            // Opcional: enviar datos actualizados al menú si es necesario
            intent.putExtra("username", username)

            // Iniciar la actividad del menú
            startActivity(intent)

            // Finalizar la actividad actual para no volver al perfil al presionar atrás
            finish()
        }

        extraImage.setOnClickListener {
            startSpeechToText()
        }
    }

    /**
     * Método para iniciar el reconocimiento de voz
     */
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
