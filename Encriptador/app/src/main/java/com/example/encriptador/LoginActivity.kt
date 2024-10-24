package com.example.encriptador

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonSignUp = findViewById<Button>(R.id.buttonSignup)
        val buttonChangePassword = findViewById<Button>(R.id.buttonChangePassword)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        val user: TextInputEditText? = findViewById(R.id.textInputEditTextUser)
        val password: TextInputEditText? = findViewById(R.id.textInputEditTextPassword)

        buttonLogin.setOnClickListener{
            val userInput = user?.text.toString().trim()
            val passwordInput = password?.text.toString().trim()

            if (userInput.isEmpty() || passwordInput.isEmpty()) {
                textViewResult.text = "Hay campos vacíos."
                return@setOnClickListener
            }
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val validacion: Cursor = bd.rawQuery("SELECT * FROM usuarios WHERE user = ? and password = ?",
                arrayOf(userInput, passwordInput))
            if(validacion.moveToFirst()) {
                val resultScreen = Intent(this, MainActivity::class.java)
                startActivity(resultScreen)
            }
            bd.close()
        }

        buttonChangePassword.setOnClickListener {
            val userInput = user?.text.toString().trim()
            val passwordInput = password?.text.toString().trim()

            if (userInput.isEmpty() || passwordInput.isEmpty()) {
                textViewResult.text = "Hay campos vacíos."
                return@setOnClickListener
            }

            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            val validacion: Cursor = bd.rawQuery("SELECT * FROM usuarios WHERE user = ?", arrayOf(userInput))

            if (validacion.moveToFirst()) {
                val contentValues = ContentValues().apply {
                    put("password", passwordInput)
                }

                val rowsAffected = bd.update("usuarios", contentValues, "user = ?", arrayOf(userInput))

                if (rowsAffected > 0) {
                    textViewResult.text = "Se ha cambiado la contraseña"
                } else {
                    textViewResult.text = "Error al cambiar la contraseña"
                }
            } else {
                textViewResult.text = "Usuario no encontrado"
            }

            validacion.close()
            bd.close()
        }

        buttonSignUp.setOnClickListener {
            val userInput = user?.text.toString().trim()
            val passwordInput = password?.text.toString().trim()

            if (userInput.isEmpty() || passwordInput.isEmpty()) {
                textViewResult.text = "Hay campos vacíos."
                return@setOnClickListener
            }
            if (passwordInput.length<8) {
                textViewResult.text = "La contraseña debe tener 8 caracteres."
                return@setOnClickListener
            }
            var mayuscula = "[A-Z]+".toRegex();
            var numeros = "[0-9]+".toRegex();
            if (!mayuscula.containsMatchIn(passwordInput)) {
                textViewResult.text = "La contraseña debe tener al menos una mayuscula."
                return@setOnClickListener
            }

            if (!numeros.containsMatchIn(passwordInput)) {
                textViewResult.text = "La contraseña debe tener al menos un número."
                return@setOnClickListener
            }

            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            val validacion: Cursor = bd.rawQuery("SELECT * FROM usuarios WHERE user = ?", arrayOf(userInput))

            if (!validacion.moveToFirst()) {
                val registro = ContentValues().apply {
                    put("user", userInput)
                    put("password", passwordInput)
                }

                bd.insert("usuarios", null, registro)
                textViewResult.text = "Se ha registrado el usuario $userInput"
            } else {
                textViewResult.text = "El usuario ya existe."
            }

            validacion.close()
            bd.close()
        }

        buttonDelete.setOnClickListener {
            val userInput = user?.text.toString().trim()
            val passwordInput = password?.text.toString().trim()

            if (userInput.isEmpty() || passwordInput.isEmpty()) {
                textViewResult.text = "Hay campos vacíos."
                return@setOnClickListener
            }

            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase

            val rowsDeleted = bd.delete("usuarios", "user = ? AND password = ?", arrayOf(userInput, passwordInput))

            if (rowsDeleted > 0) {
                textViewResult.text = "Se ha borrado la cuenta asociada a $userInput"
            } else {
                textViewResult.text = "No se encontró la cuenta para eliminar."
            }

            bd.close()
        }
    }

}
