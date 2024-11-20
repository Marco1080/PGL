package com.example.recuperacionpgl_marco

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IngresarCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ingresar_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val admin = DataBaseHelper(this, "admin", null, 1)
        val db = admin.writableDatabase
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        btnIngresar.setOnClickListener{
            var cuenta = findViewById<EditText>(R.id.etCuentaIngresar)
            var monto = findViewById<EditText>(R.id.etMontoIngresar)
            var resultado = findViewById<TextView>(R.id.tvMensajeIngresar)
            var sql = "SELECT * FROM clientes WHERE numeroCuenta = ?"
            var validacion = db.rawQuery (sql, arrayOf(cuenta.getText().toString()))
            if(validacion.moveToFirst()){
                if(validacion.getString(4) == "Estandar" && (Integer.parseInt(monto.getText().toString()) > 1000)){
                    Log.d("MensajePlan", "El plan estandar no permite ingresos mayores a 1000")
                    resultado.setText("El plan estandar no permite ingresos mayores a 1000")
                }
                else {
                    var saldo = Integer.parseInt(validacion.getString(3))
                    var montoIngresar = Integer.parseInt(monto.getText().toString())
                    var nuevoSaldo = (saldo - montoIngresar)
                    db.execSQL("UPDATE clientes SET saldo = ? WHERE numeroCuenta = ?",
                        arrayOf(nuevoSaldo, cuenta.getText().toString()))
                    resultado.setText(Integer.parseInt(validacion.getString(3)) + (Integer.parseInt(monto.getText().toString())))
                    Log.d("MensajeIngresar","Se ha realizado el ingreso de ${Integer.parseInt(validacion.getString(3)) + (Integer.parseInt(monto.getText().toString()))}")
                }
            }else{
                Log.d("MensajeIngresar","No hay cuenta asociada al numero indicado.")
                resultado.setText("No hay cuenta asociada al numero indicado.")
            }
        }
    }
}