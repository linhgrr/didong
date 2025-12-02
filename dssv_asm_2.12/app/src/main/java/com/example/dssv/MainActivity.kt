package com.example.dssv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    // Launcher cho AddStudentActivity
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val newStudent = data?.getSerializableExtra("new_student") as? Student
            if (newStudent != null) {
                studentList.add(newStudent)
                studentAdapter.notifyItemInserted(studentList.size - 1)
                Toast.makeText(this, "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Launcher cho DetailStudentActivity (Update)
    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val updatedStudent = data?.getSerializableExtra("updated_student") as? Student
            val position = data?.getIntExtra("position", -1) ?: -1

            if (updatedStudent != null && position != -1 && position < studentList.size) {
                studentList[position] = updatedStudent
                studentAdapter.notifyItemChanged(position)
                Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Xử lý nút thêm
        val btnAddMain: ImageButton = findViewById(R.id.btnAddMain)
        btnAddMain.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            addStudentLauncher.launch(intent)
        }

        recyclerView = findViewById(R.id.recyclerView)

        studentAdapter = StudentAdapter(
            studentList,
            onStudentClick = { student ->
                val position = studentList.indexOf(student)
                val intent = Intent(this, DetailStudentActivity::class.java)
                intent.putExtra("student", student)
                intent.putExtra("position", position)
                updateStudentLauncher.launch(intent)
            },
            onDeleteClick = { student ->
                val position = studentList.indexOf(student)
                if (position > -1) {
                    studentList.removeAt(position)
                    studentAdapter.notifyItemRemoved(position)
                    Toast.makeText(this, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                }
            }
        )

        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Dữ liệu mẫu
        if (studentList.isEmpty()) {
            studentList.add(Student("20200001", "Nguyễn Văn A", "0912345678", "Hà Nội"))
            studentList.add(Student("20200002", "Trần Thị B", "0987654321", "Đà Nẵng"))
            studentList.add(Student("20200003", "Lê Văn C", "0909090909", "TP HCM"))
            studentAdapter.notifyDataSetChanged()
        }
    }
}
