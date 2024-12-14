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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Obtener los elementos de la interfaz
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val extraImage = findViewById<ImageView>(R.id.extraImage)

        // Configuración del botón de guardar
        saveButton.setOnClickListener {
            // Obtener los valores de los campos
            val username = usernameInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()
            val description = descriptionInput.text.toString()

            // Aquí puedes guardar estos datos en una base de datos o realizar cualquier acción con ellos
            // Por ejemplo, puedes mostrar un mensaje o ir a otra actividad
            Toast.makeText(this, "Perfil actualizado.", Toast.LENGTH_SHORT).show()
        }

        // Configuración de la imagen (micrófono) para iniciar el reconocimiento de voz
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
                descriptionInput.setText(it[0]) // Llenar el campo de descripción con el texto reconocido
            }
        }
    }
}
