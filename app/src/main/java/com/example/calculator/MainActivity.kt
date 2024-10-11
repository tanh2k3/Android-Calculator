package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textResult: TextView
    var currentOperation: String = ""
    var operand1: Double = 0.0
    var operand2: Double = 0.0
    var isOperatorPressed = false
    var resetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)

        // Set click listeners for all buttons
        val buttonIds = intArrayOf(
            R.id.btnCE, R.id.btnC, R.id.btnBS, R.id.btnDiv,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnMul,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btnSub,
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btnAdd,
            R.id.btnNegate, R.id.btn0, R.id.btnDot, R.id.btnEqual
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 -> {
                val digit = (view as Button).text.toString()
                appendNumber(digit)
            }
            R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv -> {
                val operator = (view as Button).text.toString()
                handleOperator(operator)
            }
            R.id.btnEqual -> calculateResult()
            R.id.btnC -> clearAll()
            R.id.btnCE -> clearEntry()
            R.id.btnBS -> backspace()
            R.id.btnDot -> appendDot()
            R.id.btnNegate -> toggleSign()
        }
    }

    private fun appendNumber(number: String) {
        if (resetInput) {
            textResult.text = number
            resetInput = false
        } else {
            textResult.text = if (textResult.text == "0") number else textResult.text.toString() + number
        }
    }

    private fun handleOperator(operator: String) {
        operand1 = textResult.text.toString().toDouble()
        currentOperation = operator
        isOperatorPressed = true
        resetInput = true
    }

    private fun calculateResult() {
        operand2 = textResult.text.toString().toDouble()
        val result = when (currentOperation) {
            "+" -> operand1 + operand2
            "-" -> operand1 - operand2
            "x" -> operand1 * operand2
            "/" -> operand1 / operand2
            else -> 0.0
        }
        textResult.text = result.toString()
        resetInput = true
    }

    private fun clearAll() {
        textResult.text = "0"
        operand1 = 0.0
        operand2 = 0.0
        currentOperation = ""
        isOperatorPressed = false
    }

    private fun clearEntry() {
        textResult.text = "0"
    }

    private fun backspace() {
        val currentText = textResult.text.toString()
        if (currentText.length > 1) {
            textResult.text = currentText.dropLast(1)
        } else {
            textResult.text = "0"
        }
    }

    private fun appendDot() {
        if (!textResult.text.contains(".")) {
            textResult.text = textResult.text.toString() + "."
        }
    }

    private fun toggleSign() {
        val currentValue = textResult.text.toString().toDouble()
        textResult.text = (-currentValue).toString()
    }
}
