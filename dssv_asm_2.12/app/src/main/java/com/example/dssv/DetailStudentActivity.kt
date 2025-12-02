package com.example.dssv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)

        val etMssv = findViewById<EditText>(R.id.etMssv)
        val etName = findViewById<EditText>(R.id.etName)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Nhận dữ liệu
        val student = intent.getSerializableExtra("student") as? Student
        val position = intent.getIntExtra("position", -1)

        if (student != null) {
            etMssv.setText(student.mssv)
            etName.setText(student.hoTen)
            etPhone.setText(student.soDienThoai)
            etAddress.setText(student.diaChi)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnUpdate.setOnClickListener {
            val newMssv = etMssv.text.toString().trim()
            val newName = etName.text.toString().trim()
            val newPhone = etPhone.text.toString().trim()
            val newAddress = etAddress.text.toString().trim()

            if (newMssv.isNotEmpty() && newName.isNotEmpty()) {
                val updatedStudent = Student(newMssv, newName, newPhone, newAddress)
                val resultIntent = Intent()
                resultIntent.putExtra("updated_student", updatedStudent)
                resultIntent.putExtra("position", position)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

