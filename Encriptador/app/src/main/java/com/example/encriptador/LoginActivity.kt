package com.example.encriptador

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        val user: TextInputEditText? = findViewById(R.id.textInputEditTextUser)
        val password: TextInputEditText? = findViewById(R.id.textInputEditTextPassword)

        val buttonSignUp = findViewById<Button>(R.id.buttonSignup)
        buttonSignUp.setOnClickListener {
            if (user != null && password != null) {
                try {
                    val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                    val bd = admin.writableDatabase
                    val registro = ContentValues()
                    val validacion:Cursor = bd.rawQuery("SELECT * FROM usuarios WHERE user = ? AND password = ?",
                        arrayOf(user.text.toString(), password.text.toString()))
                    if(!validacion.moveToFirst()){
                        registro.put("user", user.text.toString())
                        registro.put("password", password.text.toString())
                        bd.insert("usuarios", null, registro)

                    }
                    Log.d("menssage","Ya existe un registro con usuario: ${user.text.toString()} y contraseña: ${password.text.toString()}")
                    bd.close()

                    mostrarRegistros()
                } catch (e: Exception) {
                    Log.e("DB", "Error al insertar datos: ${e.message}")
                }
            } else {
                Log.d("Validation", "Los campos user o password están vacíos")
            }
        }
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener{
            try {
                val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val bd = admin.writableDatabase
                bd.execSQL("DELETE FROM usuarios")
                bd.close()
                Log.d("menssage","Base de datos borrada")
            } catch (e: Exception) {
                Log.e("DB", "HAS BORRADO PRODUCCION")
            }
        }
    }


    private fun mostrarRegistros() {
        try {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.readableDatabase

            val cursor: Cursor = bd.rawQuery("SELECT * FROM usuarios", null)

            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val user = cursor.getString(cursor.getColumnIndex("user"))
                    val password = cursor.getString(cursor.getColumnIndex("password"))
                    Log.d("DB", "ID: $id, User: $user, Password: $password")
                } while (cursor.moveToNext())
            } else {
                Log.d("DB", "No hay registros en la tabla")
            }

            cursor.close()
            bd.close()
        } catch (e: Exception) {
            Log.e("DB", "Error al leer los registros: ${e.message}")
        }
    }

}
