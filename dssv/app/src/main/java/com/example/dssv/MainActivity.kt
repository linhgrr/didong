package com.example.dssv

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etMssv: EditText
    private lateinit var etName: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var recyclerView: RecyclerView
    
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()
    
    private var selectedStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etMssv = findViewById(R.id.etMssv)
        etName = findViewById(R.id.etName)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

        studentAdapter = StudentAdapter(
            studentList,
            onStudentClick = { student ->
                selectedStudent = student
                etMssv.setText(student.mssv)
            },
            onDeleteClick = { student ->
                // Xóa phần tử khỏi danh sách
                val position = studentList.indexOf(student)
                if (position > -1) {
                    studentList.removeAt(position)
                    studentAdapter.notifyItemRemoved(position)
                    
                    if (selectedStudent == student) {
                        clearInput()
                    }
                    Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnAdd.setOnClickListener {
            val mssv = etMssv.text.toString().trim()
            val name = etName.text.toString().trim()

            if (mssv.isNotEmpty() && name.isNotEmpty()) {
                val exists = studentList.any { it.mssv == mssv }
                if (exists) {
                     Toast.makeText(this, "MSSV đã tồn tại!", Toast.LENGTH_SHORT).show()
                } else {
                    val newStudent = Student(mssv, name)
                    studentList.add(newStudent)
                    studentAdapter.notifyItemInserted(studentList.size - 1)
                    clearInput()
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        // Xử lý nút Update
        btnUpdate.setOnClickListener {
            if (selectedStudent != null) {
                val newMssv = etMssv.text.toString().trim()
                val newName = etName.text.toString().trim()

                if (newMssv.isNotEmpty() && newName.isNotEmpty()) {
                    // Cập nhật thông tin
                    selectedStudent?.mssv = newMssv
                    selectedStudent?.hoTen = newName
                    
                    // Tìm vị trí để notify
                    val position = studentList.indexOf(selectedStudent)
                    if (position > -1) {
                        studentAdapter.notifyItemChanged(position)
                        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        clearInput()
                    }
                } else {
                    Toast.makeText(this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearInput() {
        etMssv.text.clear()
        etName.text.clear()
        selectedStudent = null
        etMssv.requestFocus()
    }
}
