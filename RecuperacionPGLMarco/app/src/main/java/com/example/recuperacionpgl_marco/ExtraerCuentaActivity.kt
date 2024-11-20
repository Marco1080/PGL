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

class ExtraerCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extraer_cuenta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val admin = DataBaseHelper(this, "admin", null, 1)
        val db = admin.writableDatabase
        val btnExtraer = findViewById<Button>(R.id.btnExtraer)
        btnExtraer.setOnClickListener{
            var cuenta = findViewById<EditText>(R.id.etCuentaExtraer)
            var monto = findViewById<EditText>(R.id.etMontoExtraer)
            var sql = "SELECT * FROM clientes WHERE numeroCuenta = ?"
            var validacion = db.rawQuery (sql, arrayOf(cuenta.getText().toString()))
            if(validacion.moveToFirst()){
                var saldo = Integer.parseInt(validacion.getString(3))
                var montoExtraer = Integer.parseInt(monto.getText().toString())
                var tvMensajeExtraer = findViewById<TextView>(R.id.tvMensajeExtraer)
                if((saldo - montoExtraer) > 0 || (validacion.getString(4)=="Oro")) {
                    var nuevoSaldo = (saldo - montoExtraer)
                    db.execSQL("UPDATE clientes SET saldo = ? WHERE numeroCuenta = ?", arrayOf(nuevoSaldo, cuenta.getText().toString()))
                    tvMensajeExtraer.setText("El saldo actual de la cuenta es de :\n${nuevoSaldo.toString()}")

                }else {
                    Log.d("MensajeExtraer", "El saldo quedaría en negativo.")
                    tvMensajeExtraer.setText("Las cuentas estandar no pueden quedar en negativo.")
                }
            }
        }
    }
}