package com.example.instaviajes2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DataBaseHelper(this)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val registerButton = findViewById<Button>(R.id.buttonMenu)
        val noLogueado = findViewById<TextView>(R.id.noLogueado)

        noLogueado.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val email = emailInput.text.toString()
            val phone = phoneInput.text.toString()

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showMessage("Por favor, complete todos los campos")
                return@setOnClickListener
            }

            if (dbHelper.userExists(username)) {
                showMessage("El usuario ya existe")
            } else {
                if (dbHelper.insertUser(username, password, email, phone)) {
                    navigateToLogin()
                } else {
                    showMessage("Error al registrar el usuario")
                }
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

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
