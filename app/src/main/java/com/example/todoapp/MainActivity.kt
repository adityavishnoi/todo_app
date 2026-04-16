package com.example.todoapp


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var rev1: RecyclerView
    private lateinit var fabAddTask: FloatingActionButton
    private lateinit var adapter: TaskAdapter


    private val database: TaskDatabase by lazy{ TaskDatabase.getDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        fabAddTask = findViewById(R.id.fabAddTask)
        rev1 = findViewById(R.id.rev1)

        rev1.layoutManager = LinearLayoutManager(this)


        rev1.adapter = TaskAdapter(emptyList()) { taskToDelete ->

            lifecycleScope.launch(Dispatchers.IO) {
                database.taskDao().deleteTask(taskToDelete)
                launch(Dispatchers.Main) {

                    Snackbar.make(rev1, "Task Deleted", Snackbar.LENGTH_LONG).show()
                }
            }
        }

        database.taskDao().getAllTasks().observe(this, Observer { tasks ->
            adapter.updateTasks(tasks)
        })


        fabAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}