package com.example.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    
    private lateinit var spinnerFromCurrency: Spinner
    private lateinit var spinnerToCurrency: Spinner
    private lateinit var etFromAmount: EditText
    private lateinit var etToAmount: EditText
    private lateinit var ivSwap: ImageView
    private lateinit var tvExchangeRate: TextView
    
    private var isUpdating = false
    
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "JPY" to 149.50,
        "CNY" to 7.24,
        "KRW" to 1320.0,
        "THB" to 35.50,
        "SGD" to 1.35,
        "AUD" to 1.54,
        "CAD" to 1.38,
        "VND" to 25000.0
    )
    
    private val currencySymbols = listOf(
        "USD", "EUR", "GBP", "JPY", "CNY", "KRW", "THB", "SGD", "AUD", "CAD", "VND"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        initViews()
        setupSpinners()
        setupTextWatchers()
        setupSwapButton()
    }
    
    private fun initViews() {
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency)
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency)
        etFromAmount = findViewById(R.id.etFromAmount)
        etToAmount = findViewById(R.id.etToAmount)
        ivSwap = findViewById(R.id.ivSwap)
        tvExchangeRate = findViewById(R.id.tvExchangeRate)
    }
    
    private fun setupSpinners() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        spinnerFromCurrency.adapter = adapter
        spinnerToCurrency.adapter = adapter
        
        spinnerFromCurrency.setSelection(0)
        spinnerToCurrency.setSelection(10)
        
        spinnerFromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertFromTo()
                    updateExchangeRateDisplay()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        spinnerToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isUpdating) {
                    convertFromTo()
                    updateExchangeRateDisplay()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        
        updateExchangeRateDisplay()
    }
    
    private fun setupTextWatchers() {
        etFromAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && etFromAmount.hasFocus()) {
                    convertFromTo()
                }
            }
        })
        
        etToAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating && etToAmount.hasFocus()) {
                    convertToFrom()
                }
            }
        })
    }
    
    private fun setupSwapButton() {
        ivSwap.setOnClickListener {
            isUpdating = true
            
            val fromPosition = spinnerFromCurrency.selectedItemPosition
            val toPosition = spinnerToCurrency.selectedItemPosition
            
            spinnerFromCurrency.setSelection(toPosition)
            spinnerToCurrency.setSelection(fromPosition)
            
            val fromAmount = etFromAmount.text.toString()
            val toAmount = etToAmount.text.toString()
            
            etFromAmount.setText(toAmount)
            etToAmount.setText(fromAmount)
            
            isUpdating = false
            updateExchangeRateDisplay()
        }
    }
    
    private fun convertFromTo() {
        val amountText = etFromAmount.text.toString()
        if (amountText.isEmpty() || amountText == ".") {
            isUpdating = true
            etToAmount.setText("")
            isUpdating = false
            return
        }
        
        try {
            val amount = amountText.toDouble()
            val fromCurrency = currencySymbols[spinnerFromCurrency.selectedItemPosition]
            val toCurrency = currencySymbols[spinnerToCurrency.selectedItemPosition]
            
            val result = convert(amount, fromCurrency, toCurrency)
            
            isUpdating = true
            etToAmount.setText(formatAmount(result))
            isUpdating = false
        } catch (e: NumberFormatException) {
        }
    }
    
    private fun convertToFrom() {
        val amountText = etToAmount.text.toString()
        if (amountText.isEmpty() || amountText == ".") {
            isUpdating = true
            etFromAmount.setText("")
            isUpdating = false
            return
        }
        
        try {
            val amount = amountText.toDouble()
            val fromCurrency = currencySymbols[spinnerFromCurrency.selectedItemPosition]
            val toCurrency = currencySymbols[spinnerToCurrency.selectedItemPosition]
            
            val result = convert(amount, toCurrency, fromCurrency)
            
            isUpdating = true
            etFromAmount.setText(formatAmount(result))
            isUpdating = false
        } catch (e: NumberFormatException) {
        }
    }
    
    private fun convert(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        
        return (amount / fromRate) * toRate
    }
    
    private fun formatAmount(amount: Double): String {
        val df = DecimalFormat("#,##0.##")
        return df.format(amount)
    }
    
    private fun updateExchangeRateDisplay() {
        val fromCurrency = currencySymbols[spinnerFromCurrency.selectedItemPosition]
        val toCurrency = currencySymbols[spinnerToCurrency.selectedItemPosition]
        
        val rate = convert(1.0, fromCurrency, toCurrency)
        val formattedRate = formatAmount(rate)
        
        tvExchangeRate.text = "Tỷ giá: 1 $fromCurrency = $formattedRate $toCurrency"
    }
}