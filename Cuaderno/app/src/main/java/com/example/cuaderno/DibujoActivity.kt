package com.example.cuaderno

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class DibujoActivity : AppCompatActivity() {

    private var colorVertical: Int = Color.RED
    private var grosorVertical: Float = 8f
    private var colorHorizontal: Int = Color.BLUE
    private var grosorHorizontal: Float = 4f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dibujo)

        val extras = intent.extras
        if (extras != null) {
            colorVertical = getColorFromName(extras.getString("COLOR_VERTICAL", "Rojo"))
            grosorVertical = extras.getString("GROSOR_VERTICAL", "1").toFloat()

            colorHorizontal = getColorFromName(extras.getString("COLOR_HORIZONTAL", "Azul"))
            grosorHorizontal = extras.getString("GROSOR_HORIZONTAL", "1").toFloat()
        }

        val layout1 = findViewById<ConstraintLayout>(R.id.layout1)

        val fondo = Lienzo(this)
        layout1.addView(fondo)
    }

    inner class Lienzo(context: Context) : View(context) {
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            canvas.drawRGB(255, 255, 255)

            val pincelLíneaRoja = Paint()
            pincelLíneaRoja.color = colorVertical
            pincelLíneaRoja.strokeWidth = grosorVertical

            val pincelLíneasAzules = Paint()
            pincelLíneasAzules.color = colorHorizontal
            pincelLíneasAzules.strokeWidth = grosorHorizontal

            canvas.drawLine(100f, 0f, 100f, height.toFloat(), pincelLíneaRoja)

            val espacioEntreLíneas = 60f
            var y = 0f
            while (y < height) {
                canvas.drawLine(0f, y, width.toFloat(), y, pincelLíneasAzules)
                y += espacioEntreLíneas
            }
        }
    }

    private fun getColorFromName(colorName: String): Int {
        return when (colorName) {
            "Rojo" -> Color.RED
            "Verde" -> Color.GREEN
            "Azul" -> Color.BLUE
            else -> Color.BLACK
        }
    }
}
