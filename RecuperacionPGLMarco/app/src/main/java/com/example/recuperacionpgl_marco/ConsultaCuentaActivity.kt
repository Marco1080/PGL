package com.example.recuperacionpgl_marco

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConsultaCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consulta_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val admin = DataBaseHelper(this, "admin", null, 1)
        val db = admin.writableDatabase
        val btnConsulta = findViewById<Button>(R.id.btnConsulta)
        btnConsulta.setOnClickListener{
            var cuenta = findViewById<EditText>(R.id.etCuentaConsulta)
            var sql = "SELECT * FROM clientes WHERE numeroCuenta = ?"
            var validacion = db.rawQuery (sql, arrayOf(cuenta.getText().toString()))
            if(validacion.moveToFirst()){
                Log.d("MensajeConsulta", validacion.getString(0) + "<-------------")
                var informacion = findViewById<TextView>(R.id.tvResultado)
                informacion.setText("Informacion de la cuenta: ${validacion.getString(0)}"
                        + "\nNombre: ${validacion.getString(1)}"
                        + "\nApellidos: ${validacion.getString(2)}"
                        + "\nSaldo: ${validacion.getString(3)}"
                        + "\nPlan bancario: ${validacion.getString(4)}" )
            }
            else{
                Log.d("ErrorConsulta", "No hay una cuenta asociada a este número<-------")
            }
        }
    }
}