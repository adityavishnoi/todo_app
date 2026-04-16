package com.example.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay

class AddTaskActivity : AppCompatActivity() {

    private lateinit var etTaskTitle: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var btnSaveTask: Button


    private val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)


        etTaskTitle = findViewById(R.id.etTaskTitle)
        etTaskDescription = findViewById(R.id.etTaskDescription)
        btnSaveTask = findViewById(R.id.btnSaveTask)


        btnSaveTask.setOnClickListener {
            saveTaskToDatabase()
        }
    }

    private fun saveTaskToDatabase() {
        val title = etTaskTitle.text.toString().trim()
        val description = etTaskDescription.text.toString().trim()

        if (title.isEmpty()) {
            etTaskTitle.error = "Title cannot be empty"
            return
        }

        val newTask = Task(taskName = title, taskDescription = description)


        lifecycleScope.launch(Dispatchers.IO) {
            database.taskDao().insertTask(newTask)

            launch(Dispatchers.Main) {
                Snackbar.make(btnSaveTask, "Task Saved!", Snackbar.LENGTH_SHORT).show()

                delay(1000)

                finish()
            }
        }
    }
}