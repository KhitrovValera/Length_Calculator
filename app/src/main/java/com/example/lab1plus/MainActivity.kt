package com.example.lab1plus

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
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



        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9),
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btnCE),
            findViewById<Button>(R.id.btnDel),
            findViewById<Button>(R.id.btnDec)
        )

        val inputTextView: TextView = findViewById(R.id.tvVvod)
        val resultTextView: TextView = findViewById(R.id.tvVivod)

        val sp1: Spinner = findViewById(R.id.spV)
        sp1.setSelection(5)
        val sp2: Spinner = findViewById(R.id.spVi)
        sp2.setSelection(3)

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                updateConversion(inputTextView, sp1, sp2, resultTextView)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        sp1.onItemSelectedListener = spinnerListener
        sp2.onItemSelectedListener = spinnerListener

        buttons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button.text.toString(), inputTextView)
                updateConversion(inputTextView, sp1, sp2, resultTextView)
            }
        }
    }

    private val toMeters = mapOf(
        "Нанометры" to 1e-9,
        "Микрон" to 1e-6,
        "Миллиметры" to 1e-3,
        "Сантиметры" to 1e-2,
        "Дециметры" to 0.1,
        "Метры" to 1.0,
        "Километры" to 1000.0,
        "Дюймы" to 0.0254,
        "Футы" to 0.3048,
        "Ярды" to 0.9144,
        "Мили" to 1609.34
    )

    private fun convertUnits(value: String, fromUnit: String, toUnit: String): String {
        val valueInMeters = value.toDoubleOrNull() ?: 0.0
        val resultInMeters = valueInMeters * (toMeters[fromUnit] ?: 1.0)
        return (resultInMeters / (toMeters[toUnit] ?: 1.0)).toString()
    }

    private fun updateConversion(inputTextView: TextView, sp1: Spinner, sp2: Spinner, resultTextView: TextView) {
        resultTextView.text = convertUnits(
            inputTextView.text.toString(),
            sp1.selectedItem.toString(),
            sp2.selectedItem.toString()
        )
    }

    private fun handleButtonClick(buttonText: String, textView: TextView) {
        when (buttonText) {
            "CE" -> textView.text = "0.0"
            "<-" -> {
                val currentText = textView.text.toString()
                textView.text = currentText.dropLast(1)
            }
            "." -> addDecimal(textView)
            else -> {
                val currentText = textView.text.toString()
                textView.text = if (currentText == "0.0") buttonText else currentText + buttonText
            }
        }
    }

    private fun addDecimal(textView: TextView) {
        if (!textView.text.contains(".")) {
            val currentText = textView.text.toString()
            textView.text = if (currentText.isEmpty() || currentText == "0.0") "0." else "$currentText."
        }
    }
}


