package com.example.notasdevoz
import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioFilePath: String = ""

    private lateinit var etNombreGrabacion: EditText
    private lateinit var btnGrabar: Button
    private lateinit var btnDetener: Button
    private lateinit var btnReproducir: Button
    private lateinit var btnBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etNombreGrabacion = findViewById(R.id.etNombreGrabacion)
        btnGrabar = findViewById(R.id.btnGrabar)
        btnDetener = findViewById(R.id.btnDetener)
        btnReproducir = findViewById(R.id.btnReproducir)
        btnBorrar = findViewById(R.id.btnBorrar)

        btnDetener.isEnabled = false
        btnReproducir.isEnabled = false
        btnBorrar.isEnabled = false

        solicitarPermisos()

        btnGrabar.setOnClickListener {
            val nombreGrabacion = etNombreGrabacion.text.toString()
            if (nombreGrabacion.isNotEmpty()) {
                audioFilePath = "${externalCacheDir?.absolutePath}/$nombreGrabacion.3gp"
                iniciarGrabacion()
            } else {
                Toast.makeText(this, "Debes dar un nombre", Toast.LENGTH_SHORT).show()
            }
        }

        btnDetener.setOnClickListener {
            detenerGrabacion()
            Log.d("Ruta del audio", audioFilePath)
        }

        btnReproducir.setOnClickListener {
            reproducirGrabacion()
        }

        btnBorrar.setOnClickListener {
            borrarGrabacion()
        }
    }

    private fun solicitarPermisos() {
        val permisos = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (!permisos.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            ActivityCompat.requestPermissions(this, permisos, 0)
        }
    }

    private fun iniciarGrabacion() {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(audioFilePath)

            try {
                prepare()
                start()
                btnGrabar.isEnabled = false
                btnDetener.isEnabled = true
                btnReproducir.isEnabled = false
                btnBorrar.isEnabled = false
                Toast.makeText(this@MainActivity, "---> Grabando <---", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun detenerGrabacion() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        btnGrabar.isEnabled = true
        btnDetener.isEnabled = false
        btnReproducir.isEnabled = true
        btnBorrar.isEnabled = true
        Toast.makeText(this, "Grabación detenida.", Toast.LENGTH_SHORT).show()
    }

    private fun reproducirGrabacion() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(audioFilePath)
                prepare()
                start()
                Toast.makeText(this@MainActivity, "---> Reproduciendo <---", Toast.LENGTH_SHORT).show()
                setOnCompletionListener {
                    Toast.makeText(this@MainActivity, "Reproducción finalizada.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun borrarGrabacion() {
        val archivo = File(audioFilePath)
        if (archivo.exists() && archivo.delete()) {
            btnReproducir.isEnabled = false
            btnBorrar.isEnabled = false
            Toast.makeText(this, "Grabación borrada.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se pudo borrar la grabación.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaRecorder?.release()
        mediaPlayer?.release()
    }
}
