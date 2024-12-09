package com.example.instaviajes

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dbHelper = DataBaseHelper(this)

        val etName = findViewById<EditText>(R.id.etNameRegister)
        val etEmail = findViewById<EditText>(R.id.etEmailRegister)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegister)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPasswordRegister)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.writableDatabase

            val cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE email = ?",
                arrayOf(email)
            )

            if (cursor.count > 0) {
                Toast.makeText(this, "El correo ya está registrado", Toast.LENGTH_SHORT).show()
                cursor.close()
                return@setOnClickListener
            }

            cursor.close()

            val values = ContentValues().apply {
                put("nombre", name)
                put(
                    "apellidos",
                    "ApellidoDesconocido"
                )
                put("email", email)
                put("password", password)
            }

            val newRowId = db.insert("usuarios", null, values)

            if (newRowId != -1L) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
