package com.example.register

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var genderGroup: RadioGroup
    private lateinit var birthdayInput: EditText
    private lateinit var selectBirthdayBtn: Button
    private lateinit var calendarView: CalendarView
    private lateinit var addressInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var termsCheckbox: CheckBox
    private lateinit var registerBtn: Button
    
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                v.paddingBottom
            )
            insets
        }

        initViews()
        setupListeners()
    }
    
    private fun initViews() {
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        genderGroup = findViewById(R.id.genderGroup)
        birthdayInput = findViewById(R.id.birthdayInput)
        selectBirthdayBtn = findViewById(R.id.selectBirthdayBtn)
        calendarView = findViewById(R.id.calendarView)
        addressInput = findViewById(R.id.addressInput)
        emailInput = findViewById(R.id.emailInput)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        registerBtn = findViewById(R.id.registerBtn)
        calendarView.visibility = View.GONE
    }
    
    private fun setupListeners() {
        selectBirthdayBtn.setOnClickListener {
            Log.d("MainActivity", "Select button clicked")
            Log.d("MainActivity", "CalendarView current visibility: ${calendarView.visibility}")
            
            when (calendarView.visibility) {
                View.GONE, View.INVISIBLE -> {
                    calendarView.visibility = View.VISIBLE
                    Log.d("MainActivity", "CalendarView set to VISIBLE")
                }
                else -> {
                    calendarView.visibility = View.GONE
                    Log.d("MainActivity", "CalendarView set to GONE")
                }
            }
        }
        
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            birthdayInput.setText(dateFormat.format(calendar.time))
            calendarView.visibility = View.GONE
        }
        
        termsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            registerBtn.isEnabled = isChecked
        }
        
        registerBtn.setOnClickListener {
            validateAndRegister()
        }
    }
    
    private fun validateAndRegister() {
        var isValid = true
        
        firstNameInput.setBackgroundColor(Color.WHITE)
        lastNameInput.setBackgroundColor(Color.WHITE)
        genderGroup.setBackgroundColor(Color.TRANSPARENT)
        birthdayInput.setBackgroundColor(Color.WHITE)
        addressInput.setBackgroundColor(Color.WHITE)
        emailInput.setBackgroundColor(Color.WHITE)
        
        if (firstNameInput.text.toString().trim().isEmpty()) {
            firstNameInput.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (lastNameInput.text.toString().trim().isEmpty()) {
            lastNameInput.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (genderGroup.checkedRadioButtonId == -1) {
            genderGroup.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (birthdayInput.text.toString().trim().isEmpty()) {
            birthdayInput.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (addressInput.text.toString().trim().isEmpty()) {
            addressInput.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (emailInput.text.toString().trim().isEmpty()) {
            emailInput.setBackgroundColor(Color.RED)
            isValid = false
        }
        
        if (isValid) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_LONG).show()
        }
    }
}
