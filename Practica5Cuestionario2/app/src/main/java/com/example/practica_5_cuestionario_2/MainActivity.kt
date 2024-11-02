package com.example.practica_5_cuestionario_2

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
        val bd = admin.writableDatabase

        val texResult = findViewById<TextView>(R.id.textViewResult)
        val buttonLog = findViewById<Button>(R.id.buttonLog)
        val buttonSign = findViewById<Button>(R.id.buttonSign)
        val inputUser = findViewById<EditText>(R.id.editTextUser)
        val inputPass = findViewById<EditText>(R.id.editTextPassword)

        val numeros = "[0-9]+".toRegex()

        buttonLog.setOnClickListener {
            when {
                inputUser.text.isEmpty() -> texResult.text = "Debe indicar un usuario."
                !numeros.containsMatchIn(inputUser.text) -> texResult.text = "Debe haber al menos un numero."
                inputUser.text.length < 4 -> texResult.text = "Debe tener al menos cuatro caracteres el usuario."
                inputPass.text.isEmpty() -> texResult.text = "Debe facilitar la contraseña."
                !numeros.containsMatchIn(inputPass.text) -> texResult.text = "Debe haber al menos un numero en la contraseña."
                inputPass.text.length < 4 -> texResult.text = "Debe tener al menos cuatro caracteres la contraseña."
                else -> {
                    val validacion: Cursor = bd.rawQuery(
                        "SELECT * FROM usuarios WHERE user = ? AND password = ?",
                        arrayOf(inputUser.text.toString(), inputPass.text.toString())
                    )
                    if (validacion.moveToFirst()) {
                        texResult.text = "El usuario existe, logueado."
                        val login = Intent(this, PerfilUsuario::class.java)
                        login.putExtra("user", inputUser.text.toString())
                        login.putExtra("nota", validacion.getInt(validacion.getColumnIndexOrThrow("nota")))
                        startActivity(login)
                    } else {
                        texResult.text = "El usuario no existe."
                    }
                    validacion.close()
                }
            }
        }

        buttonSign.setOnClickListener {
            val validacion: Cursor = bd.rawQuery(
                "SELECT * FROM usuarios WHERE user = ?",
                arrayOf(inputUser.text.toString())
            )
            if (!validacion.moveToFirst()) {
                val registro = ContentValues().apply {
                    put("user", inputUser.text.toString())
                    put("password", inputPass.text.toString())
                    put("nota", 0)
                }
                bd.insert("usuarios", null, registro)
                texResult.text = "Se ha guardado el usuario."
            } else {
                texResult.text = "El usuario ya está registrado."
            }
            validacion.close()
        }
    }
}
