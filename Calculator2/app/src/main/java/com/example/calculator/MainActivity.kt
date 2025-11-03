package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var textDisplay: TextView
    private lateinit var bsButton: Button
    private lateinit var cButton: Button
    private lateinit var ceButton: Button

    private var num1: Int = 0
    private var num2: Int = 0
    private var currentOperation: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bsButton = findViewById<Button>(R.id.btnBS)
        cButton = findViewById<Button>(R.id.btnC)
        ceButton = findViewById<Button>(R.id.btnCE)

        textDisplay = findViewById<TextView>(R.id.tvDisplay)

        //  handle digit number
        val digitClickListener = View.OnClickListener { v ->
            val b = v as Button
            val currentText = textDisplay.text.toString()
            // Nếu màn hình đang hiển thị "0" hoặc vừa tính toán xong, thay thế bằng số mới
            if (currentText == "0" || currentOperation.isEmpty() && num1 != 0 && textDisplay.text.toString() == num1.toString()) {
                textDisplay.text = b.text.toString()
            } else {
                textDisplay.text = currentText + b.text.toString()
            }
        }

        val digitIds = intArrayOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        digitIds.forEach { id ->
            findViewById<Button>(id).setOnClickListener(digitClickListener)
        }

        //  handle functional button

        findViewById<Button>(R.id.btnBS).setOnClickListener {
            val currentText = textDisplay.text.toString()
            if (currentText.isNotEmpty() && currentText != "0") {
                textDisplay.text = currentText.dropLast(1)
                if (textDisplay.text.isEmpty()) {
                    textDisplay.text = "0"
                }
            }
        }

        findViewById<Button>(R.id.btnC).setOnClickListener {
            textDisplay.text = "0"
            num1 = 0
            num2 = 0
            currentOperation = ""
        }

        findViewById<Button>(R.id.btnCE).setOnClickListener {
            textDisplay.text = "0"
        }

        // handle operation button
        val opMap = mapOf(
            R.id.btnAdd to "+",
            R.id.btnSub to "-",
            R.id.btnMul to "×",
            R.id.btnDiv to "/"
        )
        val opClickListener = View.OnClickListener { v ->
            val symbol = opMap[v.id] ?: return@OnClickListener
            val currentText = textDisplay.text.toString()
            if (currentText.isNotEmpty() && currentText != "0") {
                num1 = currentText.toIntOrNull() ?: 0
            }
            textDisplay.text = "0"
            currentOperation = symbol
        }
        opMap.keys.forEach { id ->
            findViewById<Button>(id).setOnClickListener(opClickListener)
        }

        // handle equal button
        findViewById<Button>(R.id.btnEq).setOnClickListener {
            if (currentOperation.isEmpty()){
                num1 = textDisplay.text.toString().toIntOrNull() ?: 0
                return@setOnClickListener
            }

            val currentText = textDisplay.text.toString()
            if (currentText.isNotEmpty() && currentText != "0") {
                num2 = currentText.toIntOrNull() ?: 0
            }
            val result = when (currentOperation) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "×" -> num1 * num2
                "/" -> if (num2 != 0) num1 / num2 else 0
                else -> 0
            }
            textDisplay.text = result.toString()
            num1 = result
            num2 = 0
            currentOperation = ""
        }

        // handle +/- button
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener {
            val s = textDisplay.text.toString()

            if (s.isEmpty() || s == "0") return@setOnClickListener

            val flipped = if (s.startsWith("-")) s.substring(1) else "-$s"
            textDisplay.text = flipped

            if (currentOperation.isEmpty()) {
                num1 = flipped.toIntOrNull() ?: 0
            } else {
                num2 = flipped.toIntOrNull() ?: 0
            }
        }
    }
}
