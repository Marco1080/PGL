package com.example.instaviajes

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goToRegister = findViewById<TextView>(R.id.goToRegister)
        goToRegister.setOnClickListener {
            val pantallaRegistro = Intent(this, RegisterActivity::class.java)
            startActivity(pantallaRegistro)
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etEmailLogin)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DataBaseHelper(this)
            val db = dbHelper.readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE email = ? AND password = ?",
                arrayOf(email, password)
            )

            if (cursor.moveToFirst()) {
                val userIdIndex = cursor.getColumnIndex("idUsuario")
                if (userIdIndex != -1) {
                    val userId = cursor.getInt(userIdIndex)

                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    cursor.close()
                    db.close()

                    val pantallaMenu = Intent(this, MenuActivity::class.java)
                    pantallaMenu.putExtra("USER_ID", userId)
                    startActivity(pantallaMenu)
                    finish()
                } else {
                    Toast.makeText(this, "Error: columna 'idUsuario' no encontrada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                cursor.close()
                db.close()
            }
        }
    }
}
