package com.example.dssv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val etMssv = findViewById<EditText>(R.id.etMssv)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val mssv = etMssv.text.toString().trim()
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (mssv.isNotEmpty() && name.isNotEmpty()) {
                val student = Student(mssv, name, phone, address)
                val intent = Intent()
                intent.putExtra("new_student", student)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Vui lòng nhập ít nhất MSSV và Họ tên", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

