package com.example.displaynumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edtLimit: EditText
    private lateinit var rg1: RadioGroup
    private lateinit var rg2: RadioGroup
    private lateinit var tvEmpty: TextView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: NumberAdapter

    enum class NumberType { ODD, EVEN, PRIME, PERFECT, SQUARE, FIBONACCI }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtLimit = findViewById(R.id.edtLimit)
        rg1 = findViewById(R.id.rgTypes)
        rg2 = findViewById(R.id.rgTypes2)
        tvEmpty = findViewById(R.id.tvEmpty)
        rv = findViewById(R.id.rvNumbers)

        adapter = NumberAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val allRadioButtons = listOf(
            findViewById<RadioButton>(R.id.rbOdd),
            findViewById<RadioButton>(R.id.rbPrime),
            findViewById<RadioButton>(R.id.rbPerfect),
            findViewById<RadioButton>(R.id.rbEven),
            findViewById<RadioButton>(R.id.rbSquare),
            findViewById<RadioButton>(R.id.rbFibo)
        )

        val exclusiveListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) return@OnCheckedChangeListener
            if (group === rg1) rg2.setOnCheckedChangeListener(null).also { rg2.clearCheck() }
            else if (group === rg2) rg1.setOnCheckedChangeListener(null).also { rg1.clearCheck() }

            rg1.setOnCheckedChangeListener(this.exclusiveListener)
            rg2.setOnCheckedChangeListener(this.exclusiveListener)

            updateList()
        }
        this.exclusiveListener = exclusiveListener
        rg1.setOnCheckedChangeListener(exclusiveListener)
        rg2.setOnCheckedChangeListener(exclusiveListener)

        edtLimit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        updateList()
    }

    private lateinit var exclusiveListener: RadioGroup.OnCheckedChangeListener

    private fun getSelectedType(): NumberType {
        return when {
            findViewById<RadioButton>(R.id.rbOdd).isChecked -> NumberType.ODD
            findViewById<RadioButton>(R.id.rbEven).isChecked -> NumberType.EVEN
            findViewById<RadioButton>(R.id.rbPrime).isChecked -> NumberType.PRIME
            findViewById<RadioButton>(R.id.rbPerfect).isChecked -> NumberType.PERFECT
            findViewById<RadioButton>(R.id.rbSquare).isChecked -> NumberType.SQUARE
            findViewById<RadioButton>(R.id.rbFibo).isChecked -> NumberType.FIBONACCI
            else -> NumberType.ODD
        }
    }

    private fun updateList() {
        val limit = edtLimit.text.toString().toIntOrNull() ?: 0
        val list = if (limit > 0) generateNumbers(limit, getSelectedType()) else emptyList()

        if (list.isEmpty()) {
            rv.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            tvEmpty.visibility = View.GONE
            rv.visibility = View.VISIBLE
        }
        adapter.submitList(list)
    }

    private fun generateNumbers(n: Int, type: NumberType): List<Int> = when (type) {
        NumberType.ODD -> (1 until n step 2).toList()
        NumberType.EVEN -> (0 until n step 2).toList()
        NumberType.PRIME -> primesBelow(n)
        NumberType.PERFECT -> perfectNumbersBelow(n)
        NumberType.SQUARE -> squaresBelow(n)
        NumberType.FIBONACCI -> fiboBelow(n)
    }

    private fun primesBelow(n: Int): List<Int> {
        if (n <= 2) return emptyList()
        val res = mutableListOf<Int>()
        for (i in 2 until n) if (isPrime(i)) res += i
        return res
    }

    private fun isPrime(x: Int): Boolean {
        if (x < 2) return false
        if (x == 2 || x == 3) return true
        if (x % 2 == 0) return false
        var i = 3
        while (i * i <= x) {
            if (x % i == 0) return false
            i += 2
        }
        return true
    }

    private fun perfectNumbersBelow(n: Int): List<Int> {
        val res = mutableListOf<Int>()
        for (i in 2 until n) {
            var sum = 1
            var d = 2
            while (d * d <= i) {
                if (i % d == 0) {
                    sum += d
                    val other = i / d
                    if (other != d) sum += other
                }
                d++
            }
            if (sum == i) res += i
        }
        return res
    }

    private fun squaresBelow(n: Int): List<Int> {
        val res = mutableListOf<Int>()
        var k = 1
        while (k * k < n) {
            res += k * k
            k++
        }
        return res
    }

    private fun fiboBelow(n: Int): List<Int> {
        if (n <= 1) return emptyList()
        val res = mutableListOf<Int>()
        var a = 1
        var b = 1
        res += 1
        while (b < n) {
            res += b
            val c = a + b
            a = b
            b = c
        }
        // Ensure all numbers are < n and unique
        return res.filter { it < n }.distinct()
    }
}