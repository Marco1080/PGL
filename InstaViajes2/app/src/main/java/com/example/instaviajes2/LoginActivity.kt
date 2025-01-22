package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val dbHelper = DataBaseHelper(this)

        val imageView: ImageView = findViewById(R.id.backgroundImage)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val noRegistrado = findViewById<TextView>(R.id.noLogueado)
        val buttonMenu = findViewById<Button>(R.id.buttonMenu)

        // Selección aleatoria de fondo
        val images = arrayOf(
            R.drawable.montania1,
            R.drawable.montania2,
            R.drawable.playa1,
            R.drawable.playa2
        )
        val randomImage = images[Random.nextInt(images.size)]
        imageView.setImageResource(randomImage)

        // Navegación al registro
        noRegistrado.setOnClickListener {
            val pantallaRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(pantallaRegistro)
        }

        // Validación de usuario y navegación al menú
        buttonMenu.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showMessage("Por favor, complete todos los campos")
                return@setOnClickListener
            }

            val userExists = dbHelper.userExists(username)
            if (userExists) {
                val cursor = dbHelper.readableDatabase.rawQuery(
                    "SELECT * FROM Users WHERE username = ? AND password = ?",
                    arrayOf(username, password)
                )
                if (cursor.moveToFirst()) {
                    // Credenciales correctas
                    val pantallaMenu = Intent(this, MenuActivity::class.java)
                    pantallaMenu.putExtra("USERNAME", username)
                    startActivity(pantallaMenu)
                    finish()
                } else {
                    showMessage("Contraseña incorrecta")
                }
                cursor.close()
            } else {
                showMessage("El usuario no existe")
            }
        }
    }

    private fun showMessage(message: String) {
        val dialog = android.app.AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .create()
        dialog.show()
    }
}
