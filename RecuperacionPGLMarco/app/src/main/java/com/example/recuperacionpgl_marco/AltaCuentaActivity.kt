package com.example.recuperacionpgl_marco

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AltaCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_alta_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val admin = DataBaseHelper(this, "admin", null, 1)
        val db = admin.writableDatabase
        val spinner = findViewById<Spinner>(R.id.spVend)
        val items = arrayOf("Oro", "Estandar")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener{
            var cuenta = findViewById<EditText>(R.id.etCuenta)
            var nombre = findViewById<EditText>(R.id.etNombre)
            var apellidos = findViewById<EditText>(R.id.etApellido)
            var saldo = findViewById<EditText>(R.id.etSaldo)
            var sql = "SELECT * FROM clientes WHERE numeroCuenta = ?"
            var validacion = db.rawQuery (sql, arrayOf(cuenta.getText().toString()))
            if(validacion.moveToFirst()){
                Log.d("ErrorAlta", "Ya hay una cuenta asociada a este número<-------")
            }else{
                db.execSQL("INSERT INTO clientes (numeroCuenta, nombreTitular, apellidos,saldo, planBancario) VALUES (?, ?, ?, ?, ?)",
                            arrayOf(cuenta.getText().toString(),
                                nombre.getText().toString(),apellidos.getText().toString(),
                                Integer.parseInt(saldo.getText().toString()),
                                spinner.selectedItem.toString()))
            }
            //,planBancario
        }

    }
}